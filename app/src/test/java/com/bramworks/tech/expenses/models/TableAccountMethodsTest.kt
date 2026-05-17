package com.bramworks.tech.expenses.models

import org.junit.Assert.assertEquals
import org.junit.Test

class TableAccountMethodsTest {

    @Test
    fun setItemCount_addsAndRemovesItems_correctly() {
        val choclo = ItemMenu("Choclo", "36000")
        val account = TableAccount(1)

        // set count to 2 -> subtotal 72000
        account.SetItemCount(choclo, 2)
        assertEquals(36000 * 2, account.calculateTotalWithOutTips())

        // set count to 0 -> should remove item
        account.SetItemCount(choclo, 0)
        assertEquals(0, account.calculateTotalWithOutTips())
    }

    @Test
    fun addItem_withTableItem_replacesExistingCount_and_ignoresInvalid() {
        val cazuela = ItemMenu("Cazuela", "10000")
        val account = TableAccount(1)

        // add TableItem with count 3
        val t1 = TableItem(cazuela, 3)
        account.AddItem(t1)
        assertEquals(10000 * 3, account.calculateTotalWithOutTips())

        // add another TableItem with same name but count 1 -> should replace to 1
        val t2 = TableItem(cazuela, 1)
        account.AddItem(t2)
        assertEquals(10000 * 1, account.calculateTotalWithOutTips())

        // adding a TableItem with non-positive count should be ignored
        val t0 = TableItem(cazuela, 0)
        account.AddItem(t0)
        // still remains 1 * price
        assertEquals(10000 * 1, account.calculateTotalWithOutTips())
    }
}

