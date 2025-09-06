package com.dog.expensetracker.data.local

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.time.LocalDate

@Entity(tableName = "expenses")
class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val category: ExpenseCategory,
    val isExpense: Boolean,
    val date: Long,
    val note: String? = null
)

