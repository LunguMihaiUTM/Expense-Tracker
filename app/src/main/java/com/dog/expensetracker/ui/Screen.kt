package com.dog.expensetracker.ui

sealed class Screen(val route: String) {
    object ExpenseList : Screen("expense_list")
    object ExpenseDetail : Screen("expense_detail")
}
