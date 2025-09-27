package com.dog.expensetracker.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dog.expensetracker.ui.common.CustomBottomNavigationBar
import com.dog.expensetracker.ui.common.TransactionSection


@Composable
fun HomeScreen(
    state: HomeContract.State,
    onEvent: (HomeContract.Event) -> Unit
) {
    Scaffold(
        bottomBar = {
            CustomBottomNavigationBar (
                onEvent = onEvent
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            TopBar()
            BalanceCard(state)
            TransactionSection(
                expenses = state.expenses,
                numOfTransaction = 5,
                onDeleteClick = {expense ->
                    onEvent(HomeContract.Event.DeleteExpense(expense))
                })
        }
    }
}

@Composable
private fun BalanceHeader(state: HomeContract.State) {
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
                text = state.totalBalance.toString(),
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
private fun IncomeExpenseRow(state: HomeContract.State) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FinancialItem(
            label = "Income",
            amount = state.totalIncome.toString(),
            isIncome = true
        )

        FinancialItem(
            label = "Expenses",
            amount = state.totalExpense.toString(),
            isIncome = false
        )
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
            imageVector = Icons.Default.Menu,
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
private fun BalanceCard(state: HomeContract.State) {
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
                BalanceHeader(state)
                IncomeExpenseRow(state)
            }
        }
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

