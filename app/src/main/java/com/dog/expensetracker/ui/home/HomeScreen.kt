package com.dog.expensetracker.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dog.expensetracker.data.local.Expense
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId



@Composable
fun HomeScreen(
    navController: NavController
) {
    val homeViewModel: HomeViewModel = hiltViewModel() // get ViewModel from Hilt

    val expenses by homeViewModel.expenses.collectAsState() // collect Flow as State


    Scaffold(
        bottomBar = {
            CustomBottomNavigationBar(
                homeViewModel,
                selectedTab = 0, // Home is selected
                onTabSelected = { tabIndex ->
                    // Handle navigation here
                    when (tabIndex) {
                        0 -> { /* Navigate to Home */ }
                        1 -> { /* Navigate to Stats */ }
                        2 -> { /* Navigate to Add Transaction */ }
                        3 -> { /* Navigate to Transactions */ }
                        4 -> { /* Navigate to Profile */ }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // This is crucial!
                .padding(horizontal = 30.dp, vertical = 25.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp) // More predictable than SpaceBetween
        ) {
            TopBar()
            BalanceCard()
            TransactionSection(expenses = expenses)
        }
    }
}

@Composable
private fun TopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Apps,
            contentDescription = "Settings"
        )

        Text(
            text = "Home",
            fontSize = 20.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.SemiBold
        )

        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notifications"
        )
    }
}

@Composable
private fun BalanceCard(
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFF98467),
                            Color(0xFFB368CA),
                            Color(0xFF4E80E7)
                        ),
                        start = Offset(400f, 0f),
                        end = Offset(0f, 400f)
                    )
                )
                .padding(28.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                BalanceHeader()
                IncomeExpenseRow()
            }
        }
    }
}

@Composable
private fun BalanceHeader() {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val balance by homeViewModel.totalBalance.collectAsState()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text(
                text = "Total Balance",
                fontSize = 17.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = balance.toString(),
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More options",
            tint = Color.White
        )
    }
}

@Composable
private fun IncomeExpenseRow() {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val income by homeViewModel.totalIncome.collectAsState()
    val expense by homeViewModel.totalExpense.collectAsState()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FinancialItem(
            label = "Income",
            amount = income.toString(),
            isIncome = true
        )

        FinancialItem(
            label = "Expenses",
            amount = expense.toString(),
            isIncome = false
        )
    }
}

@Composable
private fun FinancialItem(
    label: String,
    amount: String,
    isIncome: Boolean
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                color = Color.White,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(10.dp))
            FinancialIcon(isIncome = isIncome)
        }

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = amount,
            color = Color.White,
            fontSize = 20.sp
        )
    }
}

@Composable
private fun FinancialIcon(isIncome: Boolean) {
    Box(
        modifier = Modifier
            .size(25.dp)
            .background(
                color = Color.White.copy(alpha = 0.2f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = if (isIncome) "Income" else "Expense",
            tint = Color.White,
            modifier = Modifier
                .size(17.5.dp)
                .graphicsLayer {
                    rotationZ = if (isIncome) -90f else 90f
                }
        )
    }
}

@Composable
private fun TransactionSection(expenses: List<Expense>) {
    val homeViewModel: HomeViewModel = hiltViewModel()

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Transactions",
                fontSize = 21.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )
            Text(
                text = "See All",
                fontSize = 17.sp,
                color = Color.DarkGray
            )
        }

        Spacer(Modifier.height(20.dp))

        expenses.take(5).forEach { expense ->
            Row(modifier = Modifier.fillMaxWidth()) {
                TransactionItem(
                    name = expense.category,
                    category = expense.category,
                    amount = expense.amount,
                    date = expense.date.toLocalDateCompat(),
                    isIncome = !expense.isExpense,
                    onDeleteClick = { homeViewModel.deleteExpense(expense) }
                )
            }
            Spacer(Modifier.height(25.dp))
        }
    }
}



@Composable
private fun TransactionItem(
    name: String,
    category: String,
    amount: Double,
    date: LocalDate,
    isIncome: Boolean,
    onDeleteClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f) // let text take available space
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = Color.Gray.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "category",
                    tint = Color.Black,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = name,
                    fontSize = 18.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = date.toString(),
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Amount text
        Text(
            text = (if (isIncome) "+$" else "-$") + amount,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            color = if (isIncome) Color(0xFF2E7D32) else Color(0xFFC62828)
        )

        // Delete button
        IconButton(onClick = onDeleteClick) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete transaction",
                tint = Color.DarkGray,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}


//Bottom bar :
@Composable
fun CustomBottomNavigationBar(
    homeViewModel: HomeViewModel,
    selectedTab: Int = 0,
    onTabSelected: (Int) -> Unit = {}
) {
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
                onClick = { onTabSelected(0) },
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
                onClick = { onTabSelected(1) },
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
                    homeViewModel.addExpense(expense)  // <-- Save to DB
                    showDialog = false
                }
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}


fun Long.toLocalDateCompat(): LocalDate =
    Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
