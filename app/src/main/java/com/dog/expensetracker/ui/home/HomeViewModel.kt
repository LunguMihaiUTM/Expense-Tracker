package com.dog.expensetracker.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dog.expensetracker.data.local.Expense
import com.dog.expensetracker.data.local.ExpenseDatabase
import com.dog.expensetracker.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = ExpenseDatabase.getDatabase(application).expenseDao()
    private val repository = ExpenseRepository(dao)

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses

    init {
        loadExpenses()
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            repository.allExpenses.collect { list ->
                _expenses.value = list
            }
        }
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            repository.insertExpense(expense)
        }
    }

    val totalIncome: StateFlow<Double> =
        repository.getTotalIncome().stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    val totalExpense: StateFlow<Double> =
        repository.getTotalExpense().stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    val totalBalance: StateFlow<Double> =
        repository.getTotalBalance().stateIn(viewModelScope, SharingStarted.Lazily, 0.0)
}
