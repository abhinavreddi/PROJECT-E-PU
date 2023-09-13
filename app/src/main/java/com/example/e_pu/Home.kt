package com.example.e_pu


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Home : AppCompatActivity() {

    private lateinit var  auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        auth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

//        val welcomeTextView = findViewById<TextView>(R.id.welcomeTextView)
//        welcomeTextView.text = "welcome to my app"

        val logoutButton = findViewById<TextView>(R.id.LogoutButton)
        logoutButton.setOnClickListener {
            auth.signOut()

            val editor = sharedPreferences.edit()
            editor.putBoolean("isLoggedIn",false)
            editor.apply()

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
          }
    }
}