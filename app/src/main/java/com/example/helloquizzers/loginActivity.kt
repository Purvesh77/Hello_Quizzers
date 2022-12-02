package com.example.helloquizzers

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class loginActivity : AppCompatActivity() {
    private lateinit var etEmail:EditText
    private lateinit var etPassword:EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignup:Button
    private lateinit var mAuth:FirebaseAuth
    private lateinit var loader:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        loader = ProgressDialog(this)
        etEmail = findViewById(R.id.EmailLgin)
        etPassword = findViewById(R.id.Passwordlgin)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignup = findViewById(R.id.button2)
        supportActionBar?.hide()
        btnSignup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent);
            Toast.makeText(this,"Signup page",Toast.LENGTH_SHORT).show()
        }
        btnLogin.setOnClickListener {
            loader.setTitle("Logging in")
            loader.setMessage("Please wait")

            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString().trim()
            if(email.isEmpty()){
                etEmail.setError("Required")
                Toast.makeText(this@loginActivity,"Kindly fill email details",Toast.LENGTH_SHORT).show()
            }
            else if(pass.isEmpty()){
                etPassword.setError("Required")
                Toast.makeText(this@loginActivity,"Kindly fill the password",Toast.LENGTH_SHORT).show()
            }

            else Login(email,pass)
        }
        val currentUser = mAuth.currentUser
        if(currentUser != null){
            val intent = Intent(this@loginActivity, MainActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun Login(email: String, pass: String) {
        mAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@loginActivity, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                    val user = mAuth.currentUser
                    Toast.makeText(this@loginActivity, "Login Successful", Toast.LENGTH_SHORT).show()

                }
                else {

                    Toast.makeText(baseContext, task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }

    }
}