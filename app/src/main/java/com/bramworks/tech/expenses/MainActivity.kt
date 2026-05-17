package com.bramworks.tech.expenses

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bramworks.tech.expenses.ui.ExpenseAdapter
import com.bramworks.tech.expenses.ui.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnNewExpenses = findViewById<Button>(R.id.btnNewExpenses)
        val rvExpenses = findViewById<RecyclerView>(R.id.rvExpenses)
        val tvEmptyState = findViewById<TextView>(R.id.tvEmptyState)

        // Configurar RecyclerView
        val adapter = ExpenseAdapter { expense -> viewModel.delete(expense) }
        rvExpenses.adapter = adapter
        rvExpenses.layoutManager = LinearLayoutManager(this)

        // Observar LiveData desde Room
        viewModel.allExpenses.observe(this) { expenses ->
            adapter.submitList(expenses)
            tvEmptyState.visibility = if (expenses.isEmpty()) View.VISIBLE else View.GONE
        }

        btnNewExpenses.setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
