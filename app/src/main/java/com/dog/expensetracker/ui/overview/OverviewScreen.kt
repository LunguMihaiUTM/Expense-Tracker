package com.dog.expensetracker.ui.overview

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dog.expensetracker.data.local.ExpenseCategory
import com.dog.expensetracker.navigation.Screen
import com.dog.expensetracker.ui.common.CustomBottomNavigationBar
import com.dog.expensetracker.ui.common.TransactionSection
import com.dog.expensetracker.ui.home.HomeViewModel
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

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
                        0 -> {
                            navController.navigate(Screen.Home.route)
                        }

                        1 -> { /* Navigate to Stats */
                        }

                        2 -> { /* Navigate to Add Transaction */
                        }

                        3 -> { /* Navigate to Transactions */
                        }

                        4 -> { /* Navigate to Profile */
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            OverviewTopBar()
            OverviewSummaryCards()
            StatisticsChart()

            ExpensePieChart(
                totalsByCategory = totalsByCategory,
                chartSize = 150.dp,
                ringThickness = 20.dp
            )

            TransactionSection(expenses = expenses, -1)


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
    val homeViewModel: HomeViewModel = hiltViewModel()
    val income by homeViewModel.totalIncome.collectAsState()
    val expense by homeViewModel.totalExpense.collectAsState()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SummaryCard(
            modifier = Modifier.weight(1f),
            title = "Total Income",
            amount = income.toString(),
            backgroundColor = Color(0xFFE8E4FF),
            accentColor = Color(0xFF6C5CE7)
        )

        SummaryCard(
            modifier = Modifier.weight(1f),
            title = "Total Expenses",
            amount = expense.toString(),
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


//@Preview(showBackground = true)
//@Composable
//fun OverviewScreenPreview() {
//    OverviewScreen(rememberNavController())
//}


@Composable
fun ExpensePieChart(
    totalsByCategory: Map<ExpenseCategory, Double>,
    chartSize: Dp,
    ringThickness: Dp
) {
    val slices = totalsByCategory.values.map { it.toFloat() }
    val colors = totalsByCategory.keys.map { it.color }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
        ) {
        ExpenseChartLegend()

        PieChart(
            slices = slices,
            colors = colors,
            chartSize = chartSize,
            ringThickness = ringThickness
        )
    }
}

@Composable
fun PieChart(
    slices: List<Float>,
    colors: List<Color>,
    chartSize: Dp,
    ringThickness: Dp,   // thickness of the ring (instead of strokeWidth)
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


//Working, but need to analyze before use

//@Composable
//fun PieChart(
//    slices: List<Float>,
//    colors: List<Color>,
//    chartSize: Dp,
//    ringThickness: Dp,
//    expandedThickness: Dp = 30.dp, // thickness when clicked
//    startAngleOffset: Float = -90f,
//    onSliceClick: ((index: Int) -> Unit)? = null // callback when a slice is clicked
//) {
//    val safeSlices = slices.map { if (it.isFinite() && it > 0f) it else 0f }
//    val total = safeSlices.sum()
//
//    var selectedIndex by remember { mutableStateOf(-1) }
//
//    Box(
//        modifier = Modifier.fillMaxWidth(),
//        contentAlignment = androidx.compose.ui.Alignment.Center
//    ) {
//        Canvas(
//            modifier = Modifier
//                .size(chartSize)
//                .pointerInput(true) {
//                    detectTapGestures { tapOffset ->
//                        val center = Offset(size.width / 2f, size.height / 2f)
//                        val dx = tapOffset.x - center.x
//                        val dy = tapOffset.y - center.y // Don't invert Y here
//                        val touchRadius = sqrt(dx * dx + dy * dy)
//
//                        val radius = min(size.width, size.height) / 2f
//                        val maxThickness = max(ringThickness.toPx(), expandedThickness.toPx())
//                        val outerRadius = radius - maxThickness / 2f + maxThickness
//                        val innerRadius = radius - maxThickness / 2f
//
//                        // Check if touch is within the ring area
//                        if (touchRadius in innerRadius..outerRadius) {
//                            // Calculate angle correctly
//                            var touchAngle = atan2(dy, dx).toDegrees()
//                            if (touchAngle < 0) touchAngle += 360f
//
//                            // Adjust for start angle offset
//                            touchAngle = (touchAngle - startAngleOffset + 360f) % 360f
//
//                            var currentAngle = 0f
//                            var foundSlice = false
//
//                            safeSlices.forEachIndexed { index, value ->
//                                if (value <= 0f) return@forEachIndexed
//
//                                val sweep = (value / total) * 360f
//
//                                if (touchAngle >= currentAngle && touchAngle <= currentAngle + sweep && !foundSlice) {
//                                    selectedIndex = index
//                                    onSliceClick?.invoke(index)
//                                    foundSlice = true
//                                }
//
//                                currentAngle += sweep
//                            }
//                        }
//                    }
//                }
//        ) {
//            val diameter = min(size.width, size.height)
//            val radius = diameter / 2f
//            val center = Offset(size.width / 2f, size.height / 2f)
//
//            if (total <= 0f) {
//                drawCircle(
//                    color = Color.LightGray,
//                    radius = radius - ringThickness.toPx() / 2,
//                    center = center,
//                    style = Stroke(width = ringThickness.toPx())
//                )
//                return@Canvas
//            }
//
//            var startAngle = startAngleOffset
//            safeSlices.forEachIndexed { i, value ->
//                if (value <= 0f) return@forEachIndexed
//
//                val sweep = (value / total) * 360f
//                val color = colors.getOrElse(i) { Color.Gray }
//
//                // Calculate radius for outward expansion
//                val thickness = if (i == selectedIndex) {
//                    expandedThickness.toPx()
//                } else {
//                    ringThickness.toPx()
//                }
//
//                // For expanded slices, move the center outward so expansion goes outward
//                val baseRadius = radius - ringThickness.toPx() / 2
//                val adjustedRadius = if (i == selectedIndex) {
//                    baseRadius + (expandedThickness.toPx() - ringThickness.toPx()) / 2
//                } else {
//                    baseRadius
//                }
//
//                val topLeft = Offset(center.x - adjustedRadius, center.y - adjustedRadius)
//                val arcSize = Size(adjustedRadius * 2, adjustedRadius * 2)
//
//                drawArc(
//                    color = color,
//                    startAngle = startAngle,
//                    sweepAngle = sweep,
//                    useCenter = false,
//                    topLeft = topLeft,
//                    size = arcSize,
//                    style = Stroke(width = thickness)
//                )
//
//                startAngle += sweep
//            }
//        }
//    }
//}

// helper function: convert radians to degrees
fun Float.toDegrees() = this * 180f / PI.toFloat()


@Composable
fun ExpenseChartLegend() {

    val overviewViewModel: OverviewViewModel = hiltViewModel()

    val categories by overviewViewModel.distinctCategories.collectAsState()

    Column {
        for (category in categories) {

            Spacer(Modifier.height(7.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            shape = CircleShape,
                            color = category.color
                        )
                )

                Spacer(Modifier.width(7.dp))

                Text(
                    text = category.displayName,
                    color = Color.DarkGray,
                    fontSize = 11.sp
                )


            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun ExpenseChartLegendPreview() {
    ExpenseChartLegend()
}


