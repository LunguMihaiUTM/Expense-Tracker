package com.dog.expensetracker.features.home

import com.dog.expensetracker.data.local.Expense

interface HomeContract {
    data class State(
        val isLoading: Boolean = true,
        val expenses: List<Expense> = emptyList(),
        val showAddDialog: Boolean = false,
        val totalIncome: Double = 0.0,
        val totalExpense: Double = 0.0,
        val totalBalance: Double = 0.0
    )

    sealed interface Event {
        data object Initialize : Event
        data class AddExpense(val expense: Expense) : Event
        data class DeleteExpense(val expense: Expense) : Event
        data object ToggleAddDialog : Event
        data class ChangePeriod(val days: Int) : Event
    }

    sealed interface Action {
        data class ShowMessage(val message: String) : Action
        data object NavigateToOverview : Action
    }
}
