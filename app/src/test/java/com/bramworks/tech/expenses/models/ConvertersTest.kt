package com.bramworks.tech.expenses.models

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class ConvertersTest {

    private val converters = Converters()

    @Test
    fun `toExpenseType returns enum name - happy path`() {
        val result = converters.toExpenseType(ExpenseTypeEnum.WATER)

        assertEquals("WATER", result)
    }

    @Test
    fun `fromExpenseType converts valid enum name - happy path`() {
        val result = converters.fromExpenseType("NATURAL_GAS")

        assertEquals(ExpenseTypeEnum.NATURAL_GAS, result)
    }

    @Test
    fun `fromExpenseType throws on invalid value - sad path`() {
        assertThrows(IllegalArgumentException::class.java) {
            converters.fromExpenseType("INVALID_TYPE")
        }
    }
}

