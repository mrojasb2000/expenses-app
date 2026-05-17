package com.bramworks.tech.expenses.models

import com.bramworks.tech.expenses.R

enum class ExpenseTypeEnum(val label: String, val iconRes: Int) {
    ELECTRICAL("Eléctrico", R.drawable.electrical),
    NATURAL_GAS("Gas Natural", R.drawable.gas),
    WATER("Agua", R.drawable.water),
}
