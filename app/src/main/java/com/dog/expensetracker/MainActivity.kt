package com.dog.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dog.expensetracker.data.local.ExpenseDatabase
import com.dog.expensetracker.data.repository.ExpenseRepository
import com.dog.expensetracker.ui.Screen
import com.dog.expensetracker.ui.expense_list.ExpenseListScreen
import com.dog.expensetracker.ui.expense_detail.ExpenseDetailScreen
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerNavHost()
        }
    }
}

@Composable

fun ExpenseTrackerNavHost() {
    val navController = rememberNavController()

    // ⚡ You need to provide the repository here
    val context = LocalContext.current
    val db = ExpenseDatabase.getDatabase(context)
    val repository = ExpenseRepository(db.expenseDao())

    NavHost(
        navController = navController,
        startDestination = Screen.ExpenseList.route
    ) {
        composable(Screen.ExpenseList.route) {
            ExpenseListScreen(
                onAddExpenseClick = {
                    navController.navigate(Screen.ExpenseDetail.route)
                }
            )
        }

        composable(Screen.ExpenseDetail.route) {
            ExpenseDetailScreen(
                onSaveClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

