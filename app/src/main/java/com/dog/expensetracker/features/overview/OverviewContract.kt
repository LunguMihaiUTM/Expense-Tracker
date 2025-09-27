package com.dog.expensetracker.features.overview

import com.dog.expensetracker.data.local.Expense
import com.dog.expensetracker.data.local.ExpenseCategory

interface OverviewContract {

    data class State(
        val isLoading: Boolean = true,
        val expenses: List<Expense> = emptyList(),
        val totalsByCategory: Map<ExpenseCategory, Double> = emptyMap(),
        val distinctCategories: List<ExpenseCategory> = emptyList(),
        val periodDays: Int = 999,
        val totalIncome: Double = 0.0,
        val totalExpense: Double = 0.0,
        val totalBalance: Double = 0.0
    )

    sealed interface Event {
        data object Initialize : Event
        data class AddExpense(val expense: Expense) : Event
        data class DeleteExpense(val expense: Expense) : Event
        data class ChangePeriod(val days: Int) : Event
    }

    sealed interface Action {
        data class ShowMessage(val message: String) : Action
    }
}
