package com.dog.expensetracker.data.repository

import com.dog.expensetracker.data.local.Expense
import com.dog.expensetracker.data.local.ExpenseCategory
import com.dog.expensetracker.data.local.ExpenseDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    // Expose a Flow for reactive updates
    val allExpenses = expenseDao.getAllExpenses()

    suspend fun insertExpense(expense: Expense) {
        expenseDao.insertExpense(expense)
    }

    suspend fun updateExpense(expense: Expense) {
        expenseDao.updateExpense(expense)
    }

    suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }

    // Example: Get total spent (business logic inside repo)
    fun getTotalSpent(): Flow<Double> {
        return expenseDao.getAllExpenses().map { list ->
            list.sumOf { it.amount }
        }
    }

    fun getTotalIncome(): Flow<Double> =
        expenseDao.getAllExpenses().map { list ->
            list.filter { !it.isExpense }.sumOf { it.amount }
        }

    fun getTotalExpense(): Flow<Double> =
        expenseDao.getAllExpenses().map { list ->
            list.filter { it.isExpense }.sumOf { it.amount }
        }

    fun getTotalBalance(): Flow<Double> =
        combine(getTotalIncome(), getTotalExpense()) { income, expense ->
            income - expense
        }

    fun getTotalExpensesByCategoryAndPeriod(
        category: ExpenseCategory,
        startDate: Long,
        endDate: Long
    ): Flow<Double> =
        expenseDao.getExpensesByCategoryAndPeriod(category, startDate, endDate).map { list ->
            list.filter { it.isExpense }.sumOf { it.amount }
        }


    fun getExpensesTotalsByCategoryAndPeriod(
        startDate: Long,
        endDate: Long
    ): Flow<Map<ExpenseCategory, Double>> {
        // Map each category to its total expense
        return combine(
            ExpenseCategory.entries.map { category ->
                getTotalExpensesByCategoryAndPeriod(category, startDate, endDate)
                    .map { total -> category to total }
            }
        ) { array ->
            array.associate { it }
        }
    }

    fun getDistinctCategoriesFromExpenses(
    ): Flow<List<ExpenseCategory>> =
        expenseDao.getAllExpenses().map{list ->
            list.map { it.category }
                .distinct()
                .sortedBy { it.name }
        }


}
