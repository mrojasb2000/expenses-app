package com.bramworks.tech.expenses.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bramworks.tech.expenses.data.AppDatabase
import com.bramworks.tech.expenses.data.ExpenseRepository
import com.bramworks.tech.expenses.models.Expense
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExpenseRepository
    val allExpenses: StateFlow<List<Expense>>

    init {
        val dao = AppDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(dao)
        allExpenses = repository.allExpenses.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
    }

    fun insert(expense: Expense, onComplete: () -> Unit = {}) = viewModelScope.launch {
        repository.insert(expense)
        onComplete()
    }

    fun delete(expense: Expense) = viewModelScope.launch {
        repository.delete(expense)
    }
}

