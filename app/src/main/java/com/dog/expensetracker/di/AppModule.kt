package com.dog.expensetracker.di

import android.content.Context
import com.dog.expensetracker.data.local.ExpenseDatabase
import com.dog.expensetracker.data.repository.ExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ExpenseDatabase {
        return ExpenseDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideExpenseRepository(db: ExpenseDatabase): ExpenseRepository {
        return ExpenseRepository(db.expenseDao())
    }
}
