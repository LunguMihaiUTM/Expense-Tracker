package com.dog.expensetracker.navigation

//import com.dog.expensetracker.features.overview.OverviewController
//import com.dog.expensetracker.features.transactions.TransactionListController
//import com.dog.expensetracker.features.transactions.TransactionDetailController
// import com.dog.expensetracker.features.settings.SettingsController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dog.expensetracker.features.home.HomeController
import com.dog.expensetracker.features.overview.OverviewController
import com.dog.expensetracker.ui.bottom_bar.BottomNavTab
import com.dog.expensetracker.ui.bottom_bar.CustomBottomNavigationBar
import com.dog.expensetracker.ui.global.GlobalViewModel
import com.dog.expensetracker.ui.welcome.WelcomeScreen
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

private const val HOME = "Home"
private const val OVERVIEW = "Overview"

private const val WELCOME = "Welcome"

// 2. Navigation graph setup
@Composable
fun RootNavGraph(
    startDestination: RootNavDestinations = RootNavDestinations.Welcome
) {
    val navController = rememberNavController()
    val viewModel: GlobalViewModel = hiltViewModel()
    CompositionLocalProvider(LocalRootNavigator provides navController) {

        var currentNavItem by remember { mutableStateOf(BottomNavTab.HOME) }
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        var route: String? by remember { mutableStateOf(null) }

        val onTabSelect: (BottomNavTab) -> Unit = {
            navController.navigate(
                when (it) {
                    BottomNavTab.OVERVIEW -> RootNavDestinations.Overview
                    BottomNavTab.HOME -> RootNavDestinations.Home
                }
            ) {
                popUpTo(RootNavDestinations.Home) { inclusive = false }
                launchSingleTop = true
            }
        }


        LaunchedEffect(navBackStackEntry) {
            route = navBackStackEntry?.destination?.route
                ?.substringBefore("/")
                ?.substringAfterLast(".")
                ?.substringBefore("?")

            when (route) {
                OVERVIEW -> currentNavItem = BottomNavTab.OVERVIEW
                HOME -> currentNavItem = BottomNavTab.HOME
                else -> BottomNavTab.OVERVIEW
            }
        }

        Scaffold(
            bottomBar = {
                if (route != WELCOME) {
                    CustomBottomNavigationBar(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .fillMaxWidth(),
                        onTabSelected = onTabSelect,
                        selectedTab = currentNavItem,
                        onEvent = { viewModel.sendEvent(it) }
                    )
                }


            }
        ) { paddingValues ->
            Box(
                Modifier.fillMaxSize()
            ) {
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            bottom = paddingValues.calculateBottomPadding(),
                            top = paddingValues.calculateTopPadding()
                        )
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

    }
}

// 3. CompositionLocal to access NavController anywhere
val LocalRootNavigator = staticCompositionLocalOf<NavHostController> {
    error("NavController not initialized")
}
