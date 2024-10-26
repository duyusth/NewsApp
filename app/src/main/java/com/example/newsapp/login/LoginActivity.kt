package com.example.newsapp.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.example.newsapp.MainActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var  auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase auth
        auth = FirebaseAuth.getInstance();

        val email: EditText = findViewById(R.id.emailEditText)
        val password: EditText = findViewById(R.id.passwordEditText)
        val login: Button = findViewById(R.id.loginButton)
        val signUp: TextView = findViewById(R.id.signUpTextView)
        val anonymousLoginButton: Button = findViewById(R.id.anonymousLoginButton)
        // Xu ly su kien dang nhap
        login.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, " sign in success", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Sign in not success", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, " not fully information", Toast.LENGTH_SHORT).show()
            }
        }

        signUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        anonymousLoginButton.setOnClickListener {
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, " Sucess", Toast.LENGTH_SHORT).show()
                        // Chuyen den man hinh chinh
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Not success: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                }
        }

    }
}