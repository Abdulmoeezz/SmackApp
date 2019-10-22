package com.example.smaker.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.smaker.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
    fun LogUpClicked(view:View){

    val LogUpIntent= Intent(this, LogUpActivity::class.java)
        startActivity(LogUpIntent)
    }


}
