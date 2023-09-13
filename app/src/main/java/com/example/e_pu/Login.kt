package com.example.e_pu

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        auth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        if(sharedPreferences.getBoolean("isLoggedIn",false)) {
            val intent = Intent(this, Home:: class.java)
            startActivity(intent)
            finish()
        }

        val loginButton = findViewById<Button>(R.id.buttonLogin)
        loginButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.LoginUsername).text.toString()
            val password = findViewById<EditText>(R.id.LoginPassword).text.toString()

            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                Toast.makeText(
                    this@Login,
                    "Username and password cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            } else

            {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                            val editor = sharedPreferences.edit()
                            editor.putBoolean("isLoggedIn", true)
                            editor.apply()

                            val intent = Intent(this, Home::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                "registration failed. please try again..",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        val loginLink = findViewById<TextView>(R.id.RegisterLink)
        loginLink.setOnClickListener{
            val intent = Intent(this,Register::class.java)
            startActivity(intent)
        }


    }
}