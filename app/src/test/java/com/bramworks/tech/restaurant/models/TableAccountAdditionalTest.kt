package com.bramworks.tech.restaurant.models

import org.junit.Assert.assertEquals
import org.junit.Test

class TableAccountAdditionalTest {

    @Test
    fun addItem_replacesExisting_whenNamesMatchWithWhitespace() {
        val menuA = ItemMenu("Test", "100")
        val account = TableAccount(1)

        account.AddItem(menuA, 2)
        assertEquals(100 * 2, account.calculateTotalWithOutTips())

        // same name but with surrounding spaces should replace the existing count
        val menuB = ItemMenu(" Test ", "100")
        account.AddItem(menuB, 5)
        assertEquals(100 * 5, account.calculateTotalWithOutTips())
    }

    @Test
    fun addItem_ignoresNonPositiveCount_and_keepsExisting() {
        val menu = ItemMenu("X", "50")
        val account = TableAccount(1)

        account.AddItem(menu, 2)
        assertEquals(50 * 2, account.calculateTotalWithOutTips())

        // attempt to add with count 0 -> should be ignored (no removal)
        account.AddItem(menu, 0)
        assertEquals(50 * 2, account.calculateTotalWithOutTips())
    }

    @Test
    fun multipleDifferentItems_sumCorrectly() {
        val a = ItemMenu("A", "10")
        val b = ItemMenu("B", "20")
        val account = TableAccount(1)

        account.AddItem(a, 1)
        account.AddItem(b, 2)

        assertEquals(10 * 1 + 20 * 2, account.calculateTotalWithOutTips())
    }

    @Test
    fun calculateTips_roundingBehavior_truncatesDecimal() {
        val small = ItemMenu("S", "15")
        val account = TableAccount(1)
        account.AddItem(small, 1) // subtotal 15
        account.acceptsTips = true

        // 10% of 15 = 1.5 -> toInt() should truncate to 1
        assertEquals(1, account.calculateTips())
        assertEquals(16, account.calculateTotalWithTips())
    }
}

