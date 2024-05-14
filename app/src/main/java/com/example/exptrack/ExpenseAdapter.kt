
package com.example.exptrack

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

class ExpenseAdapter(
    private val context: Context,
    private var expenses: List<Expense>,
    private val onDeleteClickListener: (Expense) -> Unit,
    private val onEditClickListener: (Expense) -> Unit
) : BaseAdapter() {

    fun updateExpenses(newExpenses: List<Expense>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return expenses.size
    }

    override fun getItem(position: Int): Expense {
        return expenses[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("StringFormatMatches")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false)
        }

        val currentExpense = expenses[position]

        val amountTextView: TextView = itemView!!.findViewById(R.id.amountTextView)
        val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        amountTextView.text = context.getString(R.string.amount_format, currentExpense.amount)
        categoryTextView.text = currentExpense.category
        dateTextView.text = currentExpense.date
        descriptionTextView.text = currentExpense.description

        deleteButton.setOnClickListener {
            onDeleteClickListener.invoke(currentExpense)
        }

        itemView.setOnClickListener {
            onEditClickListener.invoke(currentExpense) // This handles the entire item click
        }

        return itemView
    }
}
