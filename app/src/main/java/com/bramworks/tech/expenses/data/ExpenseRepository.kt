package com.bramworks.tech.expenses.data

import androidx.lifecycle.LiveData
import com.bramworks.tech.expenses.models.Expense

class ExpenseRepository(private val dao: ExpenseDao) {

    val allExpenses: LiveData<List<Expense>> = dao.getAllExpenses()

    suspend fun insert(expense: Expense) = dao.insert(expense)

    suspend fun delete(expense: Expense) = dao.delete(expense)
}

