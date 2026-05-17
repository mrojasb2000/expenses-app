package com.bramworks.tech.expenses.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bramworks.tech.expenses.models.Expense
import com.bramworks.tech.expenses.models.ExpenseTypeEnum
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExpenseRepositoryTest {

    @Test
    fun `insert delegates to dao - happy path`() = runBlocking {
        val dao = FakeExpenseDao()
        val repository = ExpenseRepository(dao)
        val expense = Expense(type = ExpenseTypeEnum.ELECTRICAL, amount = 12000.0, createAt = "2026-05-17")

        repository.insert(expense)

        assertEquals(1, dao.inserted.size)
        assertEquals(expense, dao.inserted.first())
    }

    @Test
    fun `delete delegates to dao - happy path`() = runBlocking {
        val dao = FakeExpenseDao()
        val repository = ExpenseRepository(dao)
        val expense = Expense(type = ExpenseTypeEnum.WATER, amount = 8000.0, createAt = "2026-05-17")

        repository.delete(expense)

        assertEquals(1, dao.deleted.size)
        assertEquals(expense, dao.deleted.first())
    }

    @Test
    fun `allExpenses is exposed from dao - sad path empty list`() {
        val dao = FakeExpenseDao(initial = emptyList())
        val repository = ExpenseRepository(dao)

        val current = repository.allExpenses.value ?: emptyList()

        assertTrue(current.isEmpty())
    }

    private class FakeExpenseDao(initial: List<Expense> = emptyList()) : ExpenseDao {
        private val expensesLiveData = MutableLiveData(initial)
        val inserted = mutableListOf<Expense>()
        val deleted = mutableListOf<Expense>()

        override fun getAllExpenses(): LiveData<List<Expense>> = expensesLiveData

        override suspend fun insert(expense: Expense) {
            inserted.add(expense)
        }

        override suspend fun delete(expense: Expense) {
            deleted.add(expense)
        }
    }
}

