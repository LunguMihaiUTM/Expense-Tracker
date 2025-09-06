package com.dog.expensetracker.navigation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.dog.expensetracker.ui.home.HomeScreen
import com.dog.expensetracker.ui.overview.OverviewScreen
import com.dog.expensetracker.ui.welcome.WelcomeScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route,

//        // Default transition for ALL screens
//        enterTransition = {
//            slideInHorizontally(initialOffsetX = { it })
//        },
//        exitTransition = {
//            slideOutHorizontally(targetOffsetX = { -it })
//        },
//        popEnterTransition = {
//            slideInHorizontally(initialOffsetX = { -it })
//        },
//        popExitTransition = {
//            slideOutHorizontally(targetOffsetX = { it })
//        }
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.OverView.route) {
            OverviewScreen(navController)
        }
    }
}
