package com.bramworks.tech.expenses.models

import org.junit.Assert.assertEquals
import org.junit.Test

class TableItemTest {

    @Test
    fun calculateSubTotal_returnsCorrectValue_forPositiveCount() {
        val menu = ItemMenu("TestItem", "150")
        val tableItem = TableItem(menu, 3)

        // 150 * 3 = 450 -> returned as string
        assertEquals("450", tableItem.calculateSubTotal())
    }

    @Test
    fun calculateSubTotal_returnsZero_forNonPositiveCount() {
        val menu = ItemMenu("TestItem", "150")
        val tableItem = TableItem(menu, 0)

        assertEquals("0", tableItem.calculateSubTotal())

        tableItem.count = -2
        assertEquals("0", tableItem.calculateSubTotal())
    }
}

