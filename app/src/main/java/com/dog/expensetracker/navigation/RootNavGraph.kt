package com.dog.expensetracker.navigation

import kotlinx.serialization.Serializable

sealed interface RootNavDestinations {
    @Serializable
    data object Welcome : RootNavDestinations

    @Serializable
    data object Home : RootNavDestinations

    @Serializable
    data object Overview : RootNavDestinations

    @Serializable
    data object Transactions : RootNavDestinations

    @Serializable
    data class TransactionDetail(val transactionId: String) : RootNavDestinations

    @Serializable
    data object Settings : RootNavDestinations
}
