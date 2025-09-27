package com.dog.expensetracker.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dog.expensetracker.features.home.HomeContract
import com.dog.expensetracker.navigation.LocalRootNavigator
import com.dog.expensetracker.navigation.RootNavDestinations


@Composable
fun CustomBottomNavigationBar(
    selectedTab: Int = 0,
    onTabSelected: (Int) -> Unit = {},
    onEvent: (HomeContract.Event) -> Unit
) {
    val navController = LocalRootNavigator.current
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Bottom Navigation Bar
        NavigationBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White,
            tonalElevation = 8.dp
        ) {
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        modifier = Modifier.size(24.dp)
                    )
                },
                selected = selectedTab == 0,
                onClick = { navController.navigate(RootNavDestinations.Home) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF6C5CE7),
                    unselectedIconColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Filled.BarChart,
                        contentDescription = "Stats",
                        modifier = Modifier.size(24.dp)
                    )
                },
                selected = selectedTab == 1,
                onClick = { navController.navigate(RootNavDestinations.Overview)},
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF6C5CE7),
                    unselectedIconColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )

            // Empty space for FAB
            NavigationBarItem(
                icon = { Spacer(modifier = Modifier.size(24.dp)) },
                selected = false,
                onClick = { },
                enabled = false
            )

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Filled.AccountBalanceWallet,
                        contentDescription = "Transactions",
                        modifier = Modifier.size(24.dp)
                    )
                },
                selected = selectedTab == 3,
                onClick = { onTabSelected(3) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF6C5CE7),
                    unselectedIconColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Profile",
                        modifier = Modifier.size(24.dp)
                    )
                },
                selected = selectedTab == 4,
                onClick = { onTabSelected(4) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF6C5CE7),
                    unselectedIconColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }

        var showDialog by remember { mutableStateOf(false) }

        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-12).dp),
            containerColor = Color(0xFF6C5CE7),
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Transaction")
        }

        if (showDialog) {
            AddTransactionDialog(
                onDismiss = { showDialog = false },
                onSave = { expense ->
                    onEvent(HomeContract.Event.AddExpense(expense))  // <-- go through event system
                    showDialog = false
                }
            )
        }
    }
}