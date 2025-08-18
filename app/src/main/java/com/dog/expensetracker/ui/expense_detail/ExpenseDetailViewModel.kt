package com.dog.expensetracker.ui.expense_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dog.expensetracker.data.repository.ExpenseRepository
import com.dog.expensetracker.data.local.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    fun addExpense(amount: Double, category: String, note: String) {
        val expense = Expense(
            amount = amount,
            category = category,
            note = note,
            date = System.currentTimeMillis() // current timestamp
        )

        viewModelScope.launch {
            repository.insertExpense(expense)
        }
    }
}
