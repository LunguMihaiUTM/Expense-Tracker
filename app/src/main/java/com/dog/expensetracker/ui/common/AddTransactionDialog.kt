package com.dog.expensetracker.ui.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.dog.expensetracker.data.local.Expense
import com.dog.expensetracker.data.local.ExpenseCategory

@Composable
fun AddTransactionDialog(
    onDismiss: () -> Unit,
    onSave: (expense: Expense) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        AddTransactionDialogContent(onDismiss, onSave)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionDialogContent(
    onDismiss: () -> Unit,
    onSave: (expense: Expense) -> Unit
) {
    var amountText by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var isExpense by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(ExpenseCategory.OTHER) } // default category

    Card(
        shape = RoundedCornerShape(25.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(
                text = "Add Transaction",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )

            // Income / Expense toggle
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = { isExpense = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!isExpense) Color(0xFF2E7D32) else Color.LightGray,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(15.dp)
                ) { Text("Income") }

                Button(
                    onClick = { isExpense = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isExpense) Color(0xFFC62828) else Color.LightGray,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(15.dp)
                ) { Text("Expense") }
            }

            // Amount
            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it },
                label = { Text("Amount") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("0.00") }
            )

            // Note
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Note") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Optional") }
            )

            // Category dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedCategory.displayName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier.fillMaxWidth()
                )

                CategoryDropdown(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDismiss) {
                    Text("Cancel", color = Color.Gray)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {
                        val amount = amountText.toDoubleOrNull() ?: 0.0
                        val expense = Expense(
                            amount = amount,
                            category = selectedCategory,
                            isExpense = isExpense,
                            date = System.currentTimeMillis(),
                            note = note.ifBlank { null }
                        )
                        onSave(expense)
                    },
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6C5CE7),
                        contentColor = Color.White
                    )
                ) { Text("Save") }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddTransactionDialogContentPreview() {
    AddTransactionDialogContent(
        onDismiss = {},
        onSave = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    selectedCategory: ExpenseCategory,
    onCategorySelected: (ExpenseCategory) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedCategory.displayName,
            onValueChange = {},
            readOnly = true,
            label = { Text("Category") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            interactionSource = interactionSource
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ExpenseCategory.values().forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.displayName) },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}
