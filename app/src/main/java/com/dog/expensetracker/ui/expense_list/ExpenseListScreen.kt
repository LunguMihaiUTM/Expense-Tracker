package com.dog.expensetracker.ui.expense_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dog.expensetracker.data.local.Expense

@Composable
fun ExpenseListScreen(
    viewModel: ExpenseListViewModel = hiltViewModel(),
    onAddExpenseClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState(initial = ExpenseListState())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Total Spent: $${uiState.totalSpent}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(uiState.expenses) { expenseItem ->
                ExpenseItem(
                    expense = expenseItem,
                    onDelete = { viewModel.deleteExpense(expenseItem) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onAddExpenseClick() }) {
            Text("Add Expense")
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense, onDelete: (Expense) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "${expense.category}: $${expense.amount}")
        IconButton(onClick = { onDelete(expense) }) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete")
        }
    }
}
