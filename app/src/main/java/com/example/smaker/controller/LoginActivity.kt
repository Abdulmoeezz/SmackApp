package com.example.smaker.controller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.smaker.R
import com.example.smaker.services.AuthService
import kotlinx.android.synthetic.main.activity_log_up.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }





    fun LogUpClicked(view:View){
        val LogUpIntent= Intent(this, LogUpActivity::class.java)
        startActivity(LogUpIntent)
        finish()
    }

    fun logInClicked(view: View) {
        var email=UserEmail.text.toString()
        var password=UserPassword.text.toString()
        hideKeyboard()

        if(!email.isNullOrEmpty()&&!password.isNullOrEmpty()){
            enableSpinner(true)
            AuthService.loginUser(this,email,password){
                if(it){
                    AuthService.findUserByEmail(this){
                        if(it){
                            enableSpinner(false)
                            finish()

                        } else{
                            errorToast()
                        }

                    }

                }else{
                    errorToast()
                }


            }

        }else{
            Toast.makeText(this,"please filled the feilds first!", Toast.LENGTH_LONG).show()

        }


    }


    fun hideKeyboard(){
        val inputManager=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(inputManager.isAcceptingText){
            inputManager.hideSoftInputFromWindow(currentFocus?.windowToken,0)
        }

    }
    fun enableSpinner(enable:Boolean){
        if(enable){
            progressBar.visibility=View.VISIBLE

        }else{
            progressBar.visibility=View.INVISIBLE

        }
        UserLogUp.isEnabled=!enable


    }
    fun errorToast(){
        Toast.makeText(this,"Something went wrong,please try again.", Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }


}
