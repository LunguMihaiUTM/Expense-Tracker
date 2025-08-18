package com.dog.expensetracker.ui.expense_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ExpenseDetailScreen(
    viewModel: ExpenseDetailViewModel = hiltViewModel(),
    onSaveClick: () -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") }
        )
        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category") }
        )
        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Note") }
        )

        Button(
            onClick = {
                val amt = amount.toDoubleOrNull() ?: 0.0
                viewModel.addExpense(amt, category, note)
                onSaveClick() // navigate back
            }
        ) {
            Text("Save")
        }
    }
}


