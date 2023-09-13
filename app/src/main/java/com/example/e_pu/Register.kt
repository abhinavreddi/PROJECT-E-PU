package com.example.e_pu



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity(){
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        auth = FirebaseAuth.getInstance()

        val registerButton = findViewById<Button>(R.id.buttonRegister)
        registerButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.RegisterUsername).text.toString()
            val password = findViewById<EditText>(R.id.RegisterPassword).text.toString()

            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                // Display a toast message
                Toast.makeText(
                    this@Register,
                    "Username and password cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this, Login::class.java)
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
        val loginLink = findViewById<TextView>(R.id.LoginLink)
        loginLink.setOnClickListener{
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }
    }
}