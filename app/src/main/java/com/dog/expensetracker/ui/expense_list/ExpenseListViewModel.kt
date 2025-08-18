package com.dog.expensetracker.ui.expense_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dog.expensetracker.data.local.Expense
import com.dog.expensetracker.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseListState())
    val uiState: StateFlow<ExpenseListState> = _uiState

    init {
        // Start observing data right away
        viewModelScope.launch {
            repository.allExpenses.collect { expenses ->
                _uiState.value = _uiState.value.copy(
                    expenses = expenses,
                    totalSpent = expenses.sumOf { it.amount }
                )
            }
        }
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            repository.insertExpense(expense)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }
}
