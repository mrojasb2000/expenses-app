package com.bramworks.tech.expenses.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bramworks.tech.expenses.R
import com.bramworks.tech.expenses.models.Expense

class ExpenseAdapter(
    private val onDeleteClick: (Expense) -> Unit
) : ListAdapter<Expense, ExpenseAdapter.ExpenseViewHolder>(DiffCallback) {

    inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivIcon: ImageView = itemView.findViewById(R.id.ivExpenseIcon)
        private val tvType: TextView = itemView.findViewById(R.id.tvExpenseType)
        private val tvAmount: TextView = itemView.findViewById(R.id.tvExpenseAmount)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvExpenseDescription)
        private val tvCreateAt: TextView = itemView.findViewById(R.id.tvExpenseCreateAt)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDeleteExpense)

        fun bind(expense: Expense) {
            ivIcon.setImageResource(expense.type.iconRes)
            tvType.text = itemView.context.getString(expense.type.labelRes)
            tvAmount.text = itemView.context.getString(R.string.expense_amount_format, expense.amount)
            tvDescription.text = expense.description.ifBlank { "-" }
            tvCreateAt.text = itemView.context.getString(
                R.string.expense_created_at_format,
                expense.createAt.ifBlank { "-" }
            )
            btnDelete.setOnClickListener { onDeleteClick(expense) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Expense, newItem: Expense) = oldItem == newItem
    }
}
