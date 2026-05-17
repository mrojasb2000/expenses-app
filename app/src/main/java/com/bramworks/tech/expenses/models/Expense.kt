package com.bramworks.tech.expenses.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: ExpenseTypeEnum,
    val amount: Double,
    val description: String = ""
)
