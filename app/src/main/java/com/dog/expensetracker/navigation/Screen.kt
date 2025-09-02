package com.dog.expensetracker.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object OverView : Screen("overview")
    data object Welcome : Screen("welcome")

    data object Transactions : Screen("transactions")
    data object Settings : Screen("settings")

    // If you need arguments:
    data object TransactionDetail : Screen("transaction_detail/{transactionId}") {
        fun createRoute(transactionId: String) = "transaction_detail/$transactionId"
    }
}
