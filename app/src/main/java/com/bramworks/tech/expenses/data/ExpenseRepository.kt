package com.bramworks.tech.expenses.data

import com.bramworks.tech.expenses.models.Expense
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ExpenseRepository(
    private val dao: ExpenseDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    val allExpenses: Flow<List<Expense>> = dao.getAllExpenses()

    suspend fun insert(expense: Expense) = withContext(ioDispatcher) {
        dao.insert(expense)
    }

    suspend fun delete(expense: Expense) = withContext(ioDispatcher) {
        dao.delete(expense)
    }
}

