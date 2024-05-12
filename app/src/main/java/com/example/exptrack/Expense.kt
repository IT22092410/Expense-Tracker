package com.example.exptrack

data class Expense(
    var id: Long = -1,
    var amount: Double,
    var category: String,
    var date: String,
    var description: String
)