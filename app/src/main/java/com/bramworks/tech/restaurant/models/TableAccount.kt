package com.bramworks.tech.restaurant.models

class TableAccount(val table: Int) {
    val PERCENTAGE_TIPS: Float = 0.1f

    val _items: MutableList<TableItem> = mutableListOf()
    var acceptsTips: Boolean = false

    // For simplicity, we will assume that the price is always a valid integer string.
    fun AddItem(itemMenu: ItemMenu, count: Int){
        if (count <= 0) return

        _items.find {
            it.itemMenu.name.trim() == itemMenu.name.trim()
        }?.let {
            it.count = count
        } ?: run {
            _items.add(TableItem(itemMenu, count))
        }

    }

    // For simplicity, we will assume that the price is always a valid integer string.
    fun AddItem(tableItem: TableItem){
        if (tableItem.count <= 0) return

        _items.find {
            it.itemMenu.name == tableItem.itemMenu.name
        }?.let {
            it.count = tableItem.count
        } ?: run {
            _items.add(tableItem)
        }

    }

    // Set the absolute count for an item (replace existing count or add/remove)
    fun SetItemCount(itemMenu: ItemMenu, count: Int) {
        // If count is zero or negative, remove the item if it exists
        if (count <= 0) {
            _items.removeAll { it.itemMenu.name == itemMenu.name }
            return
        }

        _items.find { it.itemMenu.name == itemMenu.name }?.let {
            it.count = count
        } ?: run {
            _items.add(TableItem(itemMenu, count))
        }
    }

    // For simplicity, we will assume that the price is always a valid integer string.
    fun calculateTotalWithOutTips(): Int {
        var total = 0
        _items.forEach {
            total += it.itemMenu.price.toInt() * it.count
        }
        return total
    }

    // For simplicity, we will assume that the tip is always 10% of the total without tips.
    fun calculateTips(): Int {
        return if (acceptsTips) (calculateTotalWithOutTips()  * PERCENTAGE_TIPS).toInt() else 0
    }

    // For simplicity, we will assume that the tip is always 10% of the total without tips.
    fun calculateTotalWithTips(): Int {
        return calculateTotalWithOutTips() + calculateTips()
    }
}