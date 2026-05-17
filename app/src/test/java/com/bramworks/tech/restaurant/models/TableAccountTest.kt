package com.bramworks.tech.restaurant.models

import org.junit.Assert.assertEquals
import org.junit.Test

class TableAccountTest {

    @Test
    fun happyPath_calculatesSubtotalTipsAndTotal_whenTipsEnabled() {
        val choclo = ItemMenu("Choclo", "36000")
        val cazuela = ItemMenu("Cazuela", "10000")

        val account = TableAccount(1)
        // add items
        account.AddItem(choclo, 1)   // 36000
        account.AddItem(cazuela, 2)  // 20000

        // enable tips
        account.acceptsTips = true

        val expectedSubtotal = 36000 + 10000 * 2
        val expectedTips = (expectedSubtotal * 0.1f).toInt()
        val expectedTotal = expectedSubtotal + expectedTips

        assertEquals(expectedSubtotal, account.calculateTotalWithOutTips())
        assertEquals(expectedTips, account.calculateTips())
        assertEquals(expectedTotal, account.calculateTotalWithTips())
    }

    @Test
    fun sadPath_ignoresInvalidCounts_and_noTipsWhenDisabled() {
        val choclo = ItemMenu("Choclo", "36000")

        val account = TableAccount(1)
        // attempt to add invalid counts
        account.AddItem(choclo, 0)
        account.AddItem(choclo, -3)

        // tips disabled by default
        account.acceptsTips = false

        // no items should have been added, totals must be zero
        assertEquals(0, account.calculateTotalWithOutTips())
        assertEquals(0, account.calculateTips())
        assertEquals(0, account.calculateTotalWithTips())
    }
}

