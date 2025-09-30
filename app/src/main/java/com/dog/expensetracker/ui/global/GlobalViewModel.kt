package com.dog.expensetracker.ui.global

import androidx.lifecycle.viewModelScope
import com.dog.expensetracker.base.BaseViewModel
import com.dog.expensetracker.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GlobalViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : BaseViewModel<GlobalContract.State, GlobalContract.Event, GlobalContract.Action>(
    GlobalContract.State()
) {

    init {
        // Collect expenses & totals
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
        viewModelScope.launch {
            repository.getTotalBalance().collect { balance ->
                updateState { it.copy(totalBalance = balance) }
            }
        }

        sendEvent(GlobalContract.Event.Initialize)
    }

    override suspend fun handleEvent(event: GlobalContract.Event) {
        when (event) {
            is GlobalContract.Event.Initialize -> Unit
            is GlobalContract.Event.AddExpense -> {
                repository.insertExpense(event.expense)
                sendAction(GlobalContract.Action.ShowMessage("Expense added"))
            }
            is GlobalContract.Event.DeleteExpense -> {
                repository.deleteExpense(event.expense)
                sendAction(GlobalContract.Action.ShowMessage("Expense deleted"))
            }
            is GlobalContract.Event.ToggleAddDialog -> {
                updateState { it.copy(showAddDialog = !it.showAddDialog) }
            }
        }
    }
}
