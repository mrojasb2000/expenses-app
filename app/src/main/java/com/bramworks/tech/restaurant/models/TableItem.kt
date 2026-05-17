package com.bramworks.tech.restaurant.models

import java.util.Locale

class TableItem(val itemMenu: ItemMenu, var count: Int) {

    fun calculateSubTotal(): String {
        if (count <= 0) return "0"
        val price: Int = itemMenu.price.toInt()
        val subTotal: Int = price * count
        return String.format(Locale.ROOT, "%d", subTotal)
    }
}