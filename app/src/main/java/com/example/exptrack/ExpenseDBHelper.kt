package com.example.exptrack

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExpenseDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "expenses.db"
        private const val TABLE_EXPENSES = "expenses"
        private const val KEY_ID = "id"
        private const val KEY_AMOUNT = "amount"
        private const val KEY_CATEGORY = "category"
        private const val KEY_DATE = "date"
        private const val KEY_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_EXPENSES_TABLE = ("CREATE TABLE $TABLE_EXPENSES(" +
                "$KEY_ID INTEGER PRIMARY KEY," +
                "$KEY_AMOUNT REAL," +
                "$KEY_CATEGORY TEXT," +
                "$KEY_DATE TEXT," +
                "$KEY_DESCRIPTION TEXT)")
        db.execSQL(CREATE_EXPENSES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EXPENSES")
        onCreate(db)
    }

    fun addExpense(expense: Expense): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_AMOUNT, expense.amount)
            put(KEY_CATEGORY, expense.category)
            put(KEY_DATE, expense.date)
            put(KEY_DESCRIPTION, expense.description)
        }
        val id = db.insert(TABLE_EXPENSES, null, values)
        db.close()
        return id
    }

    @SuppressLint("Range")
    fun getAllExpenses(): List<Expense> {
        val expenseList = mutableListOf<Expense>()
        val selectQuery = "SELECT * FROM $TABLE_EXPENSES"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val expense = Expense(
                    id = cursor.getLong(cursor.getColumnIndex(KEY_ID)),
                    amount = cursor.getDouble(cursor.getColumnIndex(KEY_AMOUNT)),
                    category = cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)),
                    date = cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                    description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
                )
                expenseList.add(expense)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return expenseList
    }

    @SuppressLint("Range")
    fun getExpense(id: Long): Expense? {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_EXPENSES,
            arrayOf(KEY_ID, KEY_AMOUNT, KEY_CATEGORY, KEY_DATE, KEY_DESCRIPTION),
            "$KEY_ID=?",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            val expense = Expense(
                id = cursor.getLong(cursor.getColumnIndex(KEY_ID)),
                amount = cursor.getDouble(cursor.getColumnIndex(KEY_AMOUNT)),
                category = cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)),
                date = cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
            )
            cursor.close()
            expense
        } else {
            cursor.close()
            null
        }
    }

    fun updateExpense(expense: Expense): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_AMOUNT, expense.amount)
            put(KEY_CATEGORY, expense.category)
            put(KEY_DATE, expense.date)
            put(KEY_DESCRIPTION, expense.description)
        }
        val rowsAffected = db.update(TABLE_EXPENSES, values, "$KEY_ID = ?", arrayOf(expense.id.toString()))
        db.close()
        return rowsAffected
    }

    fun deleteExpense(expense: Expense): Int {
        val db = this.writableDatabase
        val rowsAffected = db.delete(TABLE_EXPENSES, "$KEY_ID = ?", arrayOf(expense.id.toString()))
        db.close()
        return rowsAffected
    }
}