package sertifikasivsga.ac.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import sertifikasivsga.ac.R
import sertifikasivsga.ac.helper.DbHelper

class RegisterActivity : AppCompatActivity() {

    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbHelper = DbHelper(this)

        val emailEditText = findViewById<EditText>(R.id.registerEmail)
        val passwordEditText = findViewById<EditText>(R.id.registerPassword)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val loginRedirect = findViewById<TextView>(R.id.loginRedirect)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (dbHelper.registerUser(email, password)) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
            }
        }

        loginRedirect.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}

