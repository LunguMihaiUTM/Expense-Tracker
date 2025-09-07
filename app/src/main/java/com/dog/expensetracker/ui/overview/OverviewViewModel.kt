package com.dog.expensetracker.ui.overview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dog.expensetracker.data.local.Expense
import com.dog.expensetracker.data.local.ExpenseCategory
import com.dog.expensetracker.data.local.ExpenseDatabase
import com.dog.expensetracker.data.repository.ExpenseRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar


class OverviewViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = ExpenseDatabase.getDatabase(application).expenseDao()
    private val repository = ExpenseRepository(dao)

    // All expenses list
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses

    // Period in days (default: last 7 days)
    private val _periodDays = MutableStateFlow(7)
    val periodDays: StateFlow<Int> = _periodDays

    // Compute totals by category for the selected period
    @OptIn(ExperimentalCoroutinesApi::class)
    val totalsFlow: StateFlow<Map<ExpenseCategory, Double>> =
        _periodDays.flatMapLatest { days ->
            val calendar = Calendar.getInstance()
            val endDate = calendar.timeInMillis
            calendar.add(Calendar.DAY_OF_YEAR, -days)
            val startDate = calendar.timeInMillis

            repository.getExpensesTotalsByCategoryAndPeriod(startDate, endDate)
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyMap())


    val distinctCategories: StateFlow<List<ExpenseCategory>> =
        repository.getDistinctCategoriesFromExpenses()
            .stateIn(
                scope = viewModelScope,       // usually inside a ViewModel
                started = SharingStarted.Lazily,
                initialValue = emptyList()    // initial value for StateFlow
            )


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

    // Functions to add/delete expenses
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

    // Function to update the period dynamically
    fun setPeriod(days: Int) {
        _periodDays.value = days
    }
}
