package com.bramworks.tech.restaurant

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.view.View
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {


    fun getIntValue(value: TextView? ): Int {
        if (value == null) return 0
        val text = value.text.toString().trim()
        if (text.isEmpty()) return 0
        return try {
            text.toInt()
        } catch (_: NumberFormatException) {
            0
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnNewExpenses = findViewById<Button>(R.id.btnNewExpenses)
        btnNewExpenses.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }

        val subtotalTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {

            }
        }

        val focusHandler = View.OnFocusChangeListener { v, hasFocus ->
            val et = v as? EditText ?: return@OnFocusChangeListener
            if (hasFocus) {

            } else {

            }
        }

         ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
             val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
             view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
             insets
         }
    }
}