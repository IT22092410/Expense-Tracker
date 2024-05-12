package com.example.exptrack



import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UserProfileActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        // Get references to TextViews
        val textViewName = findViewById<TextView>(R.id.textViewName)
        val textViewAge = findViewById<TextView>(R.id.textViewAge)
        val textViewEmail = findViewById<TextView>(R.id.textViewEmail)
        val textViewAddress = findViewById<TextView>(R.id.textViewAddress)

        // Set user profile data
        val userProfile = getUserProfile() // Assume this function retrieves user profile data
        textViewName.text = "Name: ${userProfile.name}"
        textViewAge.text = "Age: ${userProfile.age}"
        textViewEmail.text = "Email: ${userProfile.email}"
        textViewAddress.text = "Address: ${userProfile.address}"
    }

    private fun getUserProfile(): UserProfile {
        // Here you would retrieve the user profile data from your data source
        // This is just a placeholder function
        return UserProfile("John Doe", 30, "john@example.com", "123 Main St, City, Country")
    }
}
