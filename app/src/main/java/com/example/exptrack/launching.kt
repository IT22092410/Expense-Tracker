
package com.example.exptrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

class launching : AppCompatActivity() {

    private lateinit var expenseListView: ListView
    private lateinit var addExpenseButton: Button
    private lateinit var totalExpenseTextView: TextView
    private lateinit var dbHelper: ExpenseDBHelper
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launching)

        dbHelper = ExpenseDBHelper(this)

        expenseListView = findViewById(R.id.expenseListView)
        addExpenseButton = findViewById(R.id.addExpenseButton)
        totalExpenseTextView = findViewById(R.id.totalExpenseTextView)

        adapter = ExpenseAdapter(
            this,
            dbHelper.getAllExpenses(),
            onDeleteClickListener = { expense: Expense ->
                dbHelper.deleteExpense(expense)
                refreshExpenseList() // Call refreshExpenseList after deleting an expense
            },
            onEditClickListener = { expense: Expense ->
                val intent = Intent(this, AddEditExpenseActivity::class.java)
                intent.putExtra("expense_id", expense.id) // Pass the expense id for editing
                startActivity(intent)
            }
        )

        expenseListView.adapter = adapter

        addExpenseButton.setOnClickListener {
            val intent = Intent(this, AddEditExpenseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun refreshExpenseList() {
        val expenses = dbHelper.getAllExpenses()
        adapter.updateExpenses(expenses)

        // Calculate total expense
        var totalExpense = 0.0
        for (expense in expenses) {
            totalExpense += expense.amount
        }
        totalExpenseTextView.text = "Total Expense: $totalExpense"
    }

    override fun onResume() {
        super.onResume()
        refreshExpenseList() // Call refreshExpenseList when the activity is resumed
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
