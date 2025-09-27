package com.dog.expensetracker.features.home

import androidx.lifecycle.viewModelScope
import com.dog.expensetracker.base.BaseViewModel
import com.dog.expensetracker.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : BaseViewModel<HomeContract.State, HomeContract.Event, HomeContract.Action>(
    HomeContract.State()
) {

    init {
        // collect expenses & totals
        viewModelScope.launch {
            repository.allExpenses.collect { list ->
                updateState { it.copy(isLoading = false, expenses = list) }
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
        // You can compute balance derived from income/expense or collect from repo
        viewModelScope.launch {
            repository.getTotalBalance().collect { balance ->
                updateState { it.copy(totalBalance = balance) }
            }
        }

        // optionally start initialization event
        sendEvent(HomeContract.Event.Initialize)
    }

    override suspend fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.Initialize -> {
                // nothing required since collectors are running; put setup logic here
            }

            is HomeContract.Event.AddExpense -> {
                repository.insertExpense(event.expense)
                sendAction(HomeContract.Action.ShowMessage("Expense added"))
            }

            is HomeContract.Event.DeleteExpense -> {
                repository.deleteExpense(event.expense)
                sendAction(HomeContract.Action.ShowMessage("Expense deleted"))
            }

            is HomeContract.Event.ToggleAddDialog -> {
                updateState { it.copy(showAddDialog = !it.showAddDialog) }
            }

            is HomeContract.Event.ChangePeriod -> {
                // if you support period filtering, call repo or set filter state
            }
        }
    }
}
