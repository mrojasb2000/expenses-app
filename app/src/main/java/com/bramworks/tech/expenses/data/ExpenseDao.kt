package com.bramworks.tech.expenses.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bramworks.tech.expenses.models.Expense

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses ORDER BY createAt DESC")
    fun getAllExpenses(): LiveData<List<Expense>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)
}

