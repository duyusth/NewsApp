package com.example.newsapp.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.example.newsapp.MainActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // khoi tao firebase
        auth = FirebaseAuth.getInstance()

        val email: EditText = findViewById(R.id.emailEditText)
        val password : EditText = findViewById(R.id.passwordEditText)
        val register : Button = findViewById(R.id.registerButton)

        // Xu ly su kien dang ky
        register.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this){ task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                            // Chuyen toi man hinh chinh
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, " Register not success", Toast.LENGTH_SHORT).show()
                        }

                    }
            } else {
                Toast.makeText(this, " Please input fully ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}