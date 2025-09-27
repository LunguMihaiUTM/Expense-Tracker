package com.dog.expensetracker.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dog.expensetracker.features.home.HomeController
import com.dog.expensetracker.features.overview.OverviewController
import com.dog.expensetracker.ui.welcome.WelcomeScreen
//import com.dog.expensetracker.features.overview.OverviewController
//import com.dog.expensetracker.features.transactions.TransactionListController
//import com.dog.expensetracker.features.transactions.TransactionDetailController
// import com.dog.expensetracker.features.settings.SettingsController
import kotlinx.serialization.Serializable

// 1. Define destinations
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

// 2. Navigation graph setup
@Composable
fun RootNavGraph(
    startDestination: RootNavDestinations = RootNavDestinations.Welcome
) {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalRootNavigator provides navController) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.fillMaxSize()
        ) {
            composable<RootNavDestinations.Welcome> {
                WelcomeScreen()
            }
            composable<RootNavDestinations.Home> {
                HomeController()
            }
            composable<RootNavDestinations.Overview> {
                OverviewController()
            }
//            composable<RootNavDestinations.Transactions> {
//                TransactionListController()
//            }
//            composable<RootNavDestinations.TransactionDetail> { backStackEntry ->
//                val args = backStackEntry.arguments
//                // Pull transactionId from the serialized nav arguments
//                val transactionId = (args?.getString("transactionId") ?: "")
//                TransactionDetailController(transactionId)
//            }
            composable<RootNavDestinations.Settings> {
                // SettingsController()
            }
        }
    }
}

// 3. CompositionLocal to access NavController anywhere
val LocalRootNavigator = staticCompositionLocalOf<NavHostController> {
    error("NavController not initialized")
}
