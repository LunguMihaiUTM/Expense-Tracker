package com.dog.expensetracker.data.local

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.ui.graphics.Color


enum class ExpenseCategory(
    val displayName: String,
    val icon: ImageVector,
    val color: Color
) {
    HOUSING("Housing", Icons.Default.Home, Color(0xFF4CAF50)),           // green
    FOOD_GROCERIES("Food & Groceries", Icons.Default.ShoppingCart, Color(0xFFFF9800)), // orange
    TRANSPORTATION("Transportation", Icons.Default.DirectionsCar, Color(0xFF2196F3)), // blue
    HEALTH("Health & Medical", Icons.Default.Favorite, Color(0xFFE91E63)),           // pink
    DEBT("Debt & Loans", Icons.Default.AccountBalance, Color(0xFF9C27B0)),           // purple
    ENTERTAINMENT("Entertainment & Leisure", Icons.Default.Movie, Color(0xFFFFC107)), // amber
    DINING_OUT("Dining Out", Icons.Default.Restaurant, Color(0xFF795548)),           // brown
    SHOPPING("Shopping", Icons.Default.ShoppingBag, Color(0xFF3F51B5)),              // indigo
    EDUCATION("Education", Icons.Default.School, Color(0xFF00BCD4)),                  // cyan
    OTHER("Other", Icons.Default.Star, Color(0xFF607D8B));                             // blue-grey
}

