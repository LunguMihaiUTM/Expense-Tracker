package com.dog.expensetracker.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dog.expensetracker.data.local.Expense
import com.dog.expensetracker.data.local.ExpenseCategory
import com.dog.expensetracker.ui.home.HomeViewModel
import com.dog.expensetracker.ui.home.toLocalDateCompat
import java.time.LocalDate

//To see all transaction pass -1 to numOfTransaction
@Composable
fun TransactionSection(expenses: List<Expense>, numOfTransaction: Int) {
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Week",
                    fontSize = 17.sp,
                    color = Color.Gray
                )

                Spacer(Modifier.width(7.dp))

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "More"
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            items(expenses.take(if (numOfTransaction > 0) numOfTransaction else expenses.size)) { expense ->
                TransactionItem(
                    name = expense.category.displayName,
                    category = expense.category,
                    amount = expense.amount,
                    date = expense.date.toLocalDateCompat(),
                    isIncome = !expense.isExpense,
                    onDeleteClick = { homeViewModel.deleteExpense(expense)
                    }
                )
            }
        }
    }
}



@Composable
fun TransactionItem(
    name: String,
    category: ExpenseCategory,
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
                        color = category.color.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = category.icon,
                    contentDescription = category.displayName,
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
                    fontSize = 16.sp,
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
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = if (isIncome) Color(0xFF2E7D32) else Color(0xFFC62828)
        )

        // Delete button
        IconButton(onClick = onDeleteClick) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete transaction",
                tint = Color.DarkGray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun TransactionSectionPreview() {
    val expense = Expense(20, 450.0, ExpenseCategory.ENTERTAINMENT, true, 121412341, null)
    val expenseList : MutableList<Expense> = mutableListOf()
    expenseList.add(expense)
    TransactionSection(expenseList, -1)
}
