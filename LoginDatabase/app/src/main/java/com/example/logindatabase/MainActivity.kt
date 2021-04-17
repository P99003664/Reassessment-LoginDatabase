package com.example.logindatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Login.setOnClickListener {
            var myIntent = Intent(this, LoginPage::class.java)
            startActivity(myIntent)
        }
        Registration.setOnClickListener{
            Toast.makeText(this,"Registration Successful",Toast.LENGTH_LONG).show()
        }
    }
}