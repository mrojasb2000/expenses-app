package com.bramworks.tech.expenses.models

import androidx.annotation.StringRes
import com.bramworks.tech.expenses.R

enum class ExpenseTypeEnum(@param:StringRes val labelRes: Int, val iconRes: Int) {
    ELECTRICAL(R.string.expense_type_electrical, R.drawable.electrical),
    NATURAL_GAS(R.string.expense_type_natural_gas, R.drawable.gas),
    WATER(R.string.expense_type_water, R.drawable.water),
}
