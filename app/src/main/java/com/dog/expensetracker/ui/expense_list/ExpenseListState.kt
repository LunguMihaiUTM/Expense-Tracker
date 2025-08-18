package com.dog.expensetracker.ui.expense_list

import com.dog.expensetracker.data.local.Expense

// ------------------- STATE -------------------
data class ExpenseListState(
    val expenses: List<Expense> = emptyList(),
    val totalSpent: Double = 0.0,
    val isLoading: Boolean = false
)
