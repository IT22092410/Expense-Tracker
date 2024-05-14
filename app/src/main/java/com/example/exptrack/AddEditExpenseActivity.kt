
package com.example.exptrack

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddEditExpenseActivity : AppCompatActivity() {

    private lateinit var amountEditText: EditText
    private lateinit var categoryEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var dbHelper: ExpenseDBHelper

    private var expenseId: Long = -1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_expense)

        dbHelper = ExpenseDBHelper(this)

        amountEditText = findViewById(R.id.amountEditText)
        categoryEditText = findViewById(R.id.categoryEditText)
        dateEditText = findViewById(R.id.dateEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        saveButton = findViewById(R.id.saveButton)

        if (intent.hasExtra("expense_id")) {
            expenseId = intent.getLongExtra("expense_id", -1)
            val expense = dbHelper.getExpense(expenseId)
            expense?.let {
                amountEditText.setText(it.amount.toString())
                categoryEditText.setText(it.category)
                dateEditText.setText(it.date)
                descriptionEditText.setText(it.description)
            }
        }

        saveButton.setOnClickListener {
            val amountText = amountEditText.text.toString()
            val category = categoryEditText.text.toString()
            val date = dateEditText.text.toString()
            val description = descriptionEditText.text.toString()

            if (amountText.isNotBlank()) {
                val amount = amountText.toDoubleOrNull()
                if (amount != null) {
                    if (expenseId == -1L) {
                        val newExpenseId = dbHelper.addExpense(Expense(0, amount, category, date, description))
                        if (newExpenseId != -1L) {
                            setResult(RESULT_OK)
                        }
                    } else {
                        val updatedExpense = Expense(expenseId, amount, category, date, description)
                        val rowsAffected = dbHelper.updateExpense(updatedExpense)
                        if (rowsAffected > 0) {
                            setResult(RESULT_OK)
                        }
                    }
                    finish()
                } else {
                    // Handle invalid amount input
                    amountEditText.error = "Invalid amount"
                }
            } else {
                // Handle empty amount input
                amountEditText.error = "Amount cannot be empty"
            }
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

}
