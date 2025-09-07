package com.dog.expensetracker.ui.overview

import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dog.expensetracker.data.local.ExpenseCategory
import com.dog.expensetracker.navigation.Screen
import com.dog.expensetracker.ui.home.CustomBottomNavigationBar
import com.dog.expensetracker.ui.home.HomeViewModel
import com.dog.expensetracker.ui.home.TransactionSection
import kotlin.math.min

@Composable
fun OverviewScreen(navController: NavController) {
    val homeViewModel: HomeViewModel = hiltViewModel() // get ViewModel from Hilt

    val overviewViewModel: OverviewViewModel = hiltViewModel()

    val expenses by homeViewModel.expenses.collectAsState() // collect Flow as State

    val totalsByCategory by overviewViewModel.totalsFlow.collectAsState()

    Scaffold(
        bottomBar = {
            CustomBottomNavigationBar(
                homeViewModel = homeViewModel,
                selectedTab = 1, // Overview/Stats is selected
                onTabSelected = { tabIndex ->
                    // Handle navigation here
                    when (tabIndex) {
                        0 -> { navController.navigate(Screen.Home.route) }
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
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            OverviewTopBar()
            OverviewSummaryCards()
            StatisticsChart()

            ExpensePieChart(
                totalsByCategory =  totalsByCategory,
                chartSize = 150.dp,
                ringThickness = 30.dp
            )

            TransactionSection(expenses = expenses)


        }
    }
}

@Composable
private fun OverviewTopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Apps",
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = "Overview",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        // Empty space for symmetry
        Spacer(modifier = Modifier.size(24.dp))
    }
}

@Composable
private fun OverviewSummaryCards() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SummaryCard(
            modifier = Modifier.weight(1f),
            title = "Total Income",
            amount = "$8,500",
            backgroundColor = Color(0xFFE8E4FF),
            accentColor = Color(0xFF6C5CE7)
        )

        SummaryCard(
            modifier = Modifier.weight(1f),
            title = "Total Expenses",
            amount = "$3,800",
            backgroundColor = Color(0xFFFFE8E4),
            accentColor = Color(0xFFFF6B47)
        )
    }
}

@Composable
private fun SummaryCard(
    modifier: Modifier = Modifier,
    title: String,
    amount: String,
    backgroundColor: Color,
    accentColor: Color
) {
    Card(
        modifier = modifier.height(80.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(accentColor, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }

            Text(
                text = amount,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun StatisticsChart() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Statistics",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Text(
                    text = "Apr 01 - Apr 30",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Monthly",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Gray
                )
            }
        }
    }
}


@Composable
private fun LegendItem(label: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, RoundedCornerShape(2.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun RecentTransactions() {
    Column {

        OverviewTransactionItem(
            icon = Icons.Default.ShoppingCart,
            name = "Shopping",
            date = "30 Apr 2022",
            amount = "1550",
            isIncome = true,
        )

        Spacer(modifier = Modifier.height(12.dp))

        OverviewTransactionItem(
            icon = Icons.Default.ShoppingCart,
            name = "Laptop",
            date = "30 Apr 2022",
            amount = "1200",
            isIncome = false,
        )
    }
}

@Composable
private fun OverviewTransactionItem(
    icon: ImageVector,
    name: String,
    date: String,
    amount: String,
    isIncome: Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color.Gray.copy(alpha = 0.2f),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = name,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                if (date.isNotEmpty()) {
                    Text(
                        text = date,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Text(
            text = (if (isIncome) "+$" else "-$") + amount,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            color = if (isIncome) Color(0xFF2E7D32) else Color(0xFFC62828)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun OverviewScreenPreview() {
    OverviewScreen(rememberNavController())
}


@Composable
fun _PieChart(
    slices: List<Float>,
    colors: List<Color>,
    chartSize: Dp = 150.dp,
    ringThickness: Dp = 30.dp,   // thickness of the ring (instead of strokeWidth)
    startAngleOffset: Float = -90f
) {
    val safeSlices = slices.map { if (it.isFinite() && it > 0f) it else 0f }
    val total = safeSlices.sum()

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(chartSize)) {
            val diameter = min(size.width, size.height)
            val radius = diameter / 2f
            val center = Offset(size.width / 2f, size.height / 2f)

            val strokePx = ringThickness.toPx()
            val adjustedRadius = radius - strokePx / 2
            val topLeft = Offset(center.x - adjustedRadius, center.y - adjustedRadius)
            val arcSize = Size(adjustedRadius * 2, adjustedRadius * 2)


            if (total <= 0f) {
                drawCircle(
                    color = Color.LightGray,
                    radius = radius - ringThickness.toPx() / 2,
                    center = center,
                    style = Stroke(width = ringThickness.toPx())
                )
                return@Canvas
            }

            var startAngle = startAngleOffset
            for (i in safeSlices.indices) {
                val value = safeSlices[i]
                if (value <= 0f) continue

                val sweep = (value / total) * 360f
                val color = colors.getOrElse(i) { Color.Gray }

                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokePx)
                )


                startAngle += sweep
            }
        }
    }
}

@Composable
fun ExpensePieChart(
    totalsByCategory: Map<ExpenseCategory, Double>,
    chartSize: Dp = 150.dp,
    ringThickness: Dp = 30.dp
) {
    val slices = totalsByCategory.values.map { it.toFloat() }
    val colors = totalsByCategory.keys.map { it.color }

    PieChart(
        slices = slices,
        colors = colors,
        chartSize = chartSize,
        ringThickness = ringThickness
    )
}

@Composable
fun PieChart(
    slices: List<Float>,
    colors: List<Color>,
    chartSize: Dp = 150.dp,
    ringThickness: Dp = 30.dp,   // thickness of the ring (instead of strokeWidth)
    startAngleOffset: Float = -90f
) {
    val safeSlices = slices.map { if (it.isFinite() && it > 0f) it else 0f }
    val total = safeSlices.sum()

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(chartSize)) {
            val diameter = min(size.width, size.height)
            val radius = diameter / 2f
            val center = Offset(size.width / 2f, size.height / 2f)

            val strokePx = ringThickness.toPx()
            val adjustedRadius = radius - strokePx / 2
            val topLeft = Offset(center.x - adjustedRadius, center.y - adjustedRadius)
            val arcSize = Size(adjustedRadius * 2, adjustedRadius * 2)


            if (total <= 0f) {
                drawCircle(
                    color = Color.LightGray,
                    radius = radius - ringThickness.toPx() / 2,
                    center = center,
                    style = Stroke(width = ringThickness.toPx())
                )
                return@Canvas
            }

            var startAngle = startAngleOffset
            for (i in safeSlices.indices) {
                val value = safeSlices[i]
                if (value <= 0f) continue

                val sweep = (value / total) * 360f
                val color = colors.getOrElse(i) { Color.Gray }

                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokePx)
                )


                startAngle += sweep
            }
        }
    }
}