package com.bramworks.tech.expenses

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bramworks.tech.expenses.models.Expense
import com.bramworks.tech.expenses.ui.MainViewModel
import com.bramworks.tech.expenses.models.ExpenseTypeEnum
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



class FormActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private fun currentDateString(): String {
        return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form)

        val spinnerType = findViewById<AutoCompleteTextView>(R.id.spinnerType)
        val etAmount = findViewById<EditText>(R.id.etAmount)
        val etDescription = findViewById<EditText>(R.id.etDescription)
        val etCreateAt = findViewById<EditText>(R.id.etCreateAt)
        val btnSave = findViewById<Button>(R.id.btnSaveExpenses)
        val btnCancel = findViewById<Button>(R.id.btnCancel)

        if (etCreateAt.text.isNullOrBlank()) {
            etCreateAt.setText(currentDateString())
        }

        // Mapper label → enum
        val labelToEnum = ExpenseTypeEnum.entries.associateBy { it.label }
        val labels = ExpenseTypeEnum.entries.map { it.label }

        spinnerType.setAdapter(
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, labels)
        )
        // first selected by default
        spinnerType.setText(labels.first(), false)

        btnSave.setOnClickListener {
            val amountText = etAmount.text.toString().trim()
            val amount = amountText.toDoubleOrNull()

            if (amount == null || amount <= 0.0) {
                Toast.makeText(this, getString(R.string.error_invalid_amount), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedType = labelToEnum[spinnerType.text.toString()]
                ?: ExpenseTypeEnum.ELECTRICAL
            val description = etDescription.text.toString().trim()
            val createAt = etCreateAt.text.toString().trim().ifBlank { currentDateString() }

            viewModel.insert(
                Expense(
                    type = selectedType,
                    amount = amount,
                    description = description,
                    createAt = createAt
                )
            )
            finish()
        }

        btnCancel.setOnClickListener {
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
