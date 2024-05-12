package com.example.exptrack

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nextButton: Button = findViewById(R.id.buttonNext)
        nextButton.setOnClickListener {
            // Navigate to the next activity
            val intent = Intent(this, launching::class.java)
            startActivity(intent)
        }
    }
}