package com.bramworks.tech.expenses.models

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromExpenseType(value: String): ExpenseTypeEnum = ExpenseTypeEnum.valueOf(value)

    @TypeConverter
    fun toExpenseType(type: ExpenseTypeEnum): String = type.name
}

