package com.dog.expensetracker.features.overview

import androidx.lifecycle.viewModelScope
import com.dog.expensetracker.base.BaseViewModel
import com.dog.expensetracker.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import java.util.Calendar

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : BaseViewModel<
        OverviewContract.State,
        OverviewContract.Event,
        OverviewContract.Action
        >(OverviewContract.State()) {

    init {
        sendEvent(OverviewContract.Event.Initialize)
    }

    override suspend fun handleEvent(event: OverviewContract.Event) {
        when (event) {
            is OverviewContract.Event.Initialize -> {
                // Collect all expenses
                viewModelScope.launch {
                    repository.allExpenses.collectLatest { list ->
                        updateState { it.copy(isLoading = false, expenses = list) }
                    }
                }

                // Totals by category for selected period
                viewModelScope.launch {
                    state.map { it.periodDays }
                        .distinctUntilChanged()
                        .flatMapLatest { days ->
                            val calendar = Calendar.getInstance()
                            val endDate = calendar.timeInMillis
                            calendar.add(Calendar.DAY_OF_YEAR, -days)
                            val startDate = calendar.timeInMillis

                            repository.getExpensesTotalsByCategoryAndPeriod(startDate, endDate)
                        }
                        .collectLatest { totals ->
                            updateState { it.copy(totalsByCategory = totals) }
                        }
                }

                // Distinct categories
                viewModelScope.launch {
                    repository.getDistinctCategoriesFromExpenses().collectLatest { cats ->
                        updateState { it.copy(distinctCategories = cats) }
                    }
                }

                viewModelScope.launch {
                    repository.getTotalIncome().collect { income ->
                        updateState { it.copy(totalIncome = income) }
                    }
                }

                viewModelScope.launch {
                    repository.getTotalIncome().collect { income ->
                        updateState { it.copy(totalIncome = income) }
                    }
                }
                viewModelScope.launch {
                    repository.getTotalExpense().collect { expense ->
                        updateState { it.copy(totalExpense = expense) }
                    }
                }
            }

            is OverviewContract.Event.AddExpense -> {
                repository.insertExpense(event.expense)

                sendAction(OverviewContract.Action.ShowMessage("Expense added"))
            }

            is OverviewContract.Event.DeleteExpense -> {
                repository.deleteExpense(event.expense)
                sendAction(OverviewContract.Action.ShowMessage("Expense deleted"))
            }

            is OverviewContract.Event.ChangePeriod -> {
                updateState { it.copy(periodDays = event.days) }
                // trigger flow recomputation automatically
            }
        }
    }
}
