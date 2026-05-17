package com.bramworks.tech.expenses.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bramworks.tech.expenses.models.Expense
import com.bramworks.tech.expenses.models.ExpenseTypeEnum
import com.bramworks.tech.expenses.testing.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExpenseDaoIntegrationTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: ExpenseDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        dao = db.expenseDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndReadExpenses_persistsAllFields_happyPath() {
        val expenseA = Expense(
            type = ExpenseTypeEnum.WATER,
            amount = 15000.0,
            description = "Factura agua",
            createAt = "2026-05-17"
        )
        val expenseB = Expense(
            type = ExpenseTypeEnum.NATURAL_GAS,
            amount = 27000.0,
            description = "Factura gas",
            createAt = "2026-05-18"
        )

        runBlocking {
            dao.insert(expenseA)
            dao.insert(expenseB)
        }

        val expenses = dao.getAllExpenses().getOrAwaitValue()

        assertEquals(2, expenses.size)
        assertEquals(ExpenseTypeEnum.NATURAL_GAS, expenses[0].type)
        assertEquals("2026-05-18", expenses[0].createAt)
        assertEquals("Factura agua", expenses[1].description)
    }

    @Test
    fun deleteNonPersistedExpense_keepsCollectionStable_sadPath() {
        val persisted = Expense(
            type = ExpenseTypeEnum.ELECTRICAL,
            amount = 10000.0,
            description = "Luz",
            createAt = "2026-05-17"
        )
        val nonPersisted = Expense(
            id = 999,
            type = ExpenseTypeEnum.WATER,
            amount = 5000.0,
            description = "No existe",
            createAt = "2026-05-19"
        )

        runBlocking {
            dao.insert(persisted)
            dao.delete(nonPersisted)
        }

        val expenses = dao.getAllExpenses().getOrAwaitValue()

        assertEquals(1, expenses.size)
        assertTrue(expenses.any { it.description == "Luz" })
    }
}


