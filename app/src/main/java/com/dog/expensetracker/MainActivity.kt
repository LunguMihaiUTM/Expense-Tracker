package com.dog.expensetracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dog.expensetracker.data.local.ExpenseDatabase
import com.dog.expensetracker.data.repository.ExpenseRepository
import com.dog.expensetracker.navigation.NavGraph
import com.dog.expensetracker.ui.Screen
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerNavHost()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun ExpenseTrackerNavHost() {
    val navController = rememberNavController()

    val context = LocalContext.current

    val db = ExpenseDatabase.getDatabase(context)

    val repository = ExpenseRepository(db.expenseDao())

    NavGraph(navController = navController)

}

