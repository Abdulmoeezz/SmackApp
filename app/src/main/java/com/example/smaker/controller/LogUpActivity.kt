package com.example.smaker.controller

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.smaker.R
import com.example.smaker.services.AuthService
import kotlinx.android.synthetic.main.activity_log_up.*
import java.util.*


class LogUpActivity : AppCompatActivity() {


    var userAvatar ="profileDefault"
    var avatarColor ="[0.5,0.5,0.5,1]"
    val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_up) }


    fun generateUserAvatar(view:View){


        val avatar=random.nextInt(28)
        val color=random.nextInt(2)
        if(color== 1){
    userAvatar="light$avatar"
    }else{
    userAvatar="dark$avatar"
    }
        val resourceId =resources.getIdentifier(userAvatar,"drawable",packageName)
        AvatarImages?.setImageResource(resourceId)}

    fun generateBackgroundColor(view:View){

        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)

        AvatarImages?.setBackgroundColor(Color.rgb(r,g,b))
        val rSaved=r.toDouble()/255
        val gSaved=g.toDouble()/255
        val bSaved=b.toDouble()/255

        avatarColor="[$rSaved,$gSaved,$bSaved,1]"
        println(avatarColor)
    }

    fun createUserClicked(view:View){
        val email    = name.text.toString()
        val password =  password_.text.toString()
        val username = Email_.text.toString()
        ChangeAvatarImage_.isEnabled=false

       AuthService.resgisterUser(this,email,password){
           if(it){
               Toast.makeText(this,"Register Succesfully",Toast.LENGTH_SHORT).show()
               //resgisterSucess
               AuthService.loginUser(this,email,password){
                   //LoginSucess
                   if(it){
                       name.text.clear()
                       password_.text.clear()
                       Email_.text.clear()
                       Toast.makeText(this,"LogIn Succesfully : ${AuthService.user} : ${AuthService.authToken}",Toast.LENGTH_SHORT).show()
                       AuthService.createUser(this,username,email,userAvatar,avatarColor){
                           if(it){
                               ChangeAvatarImage_.isEnabled=true
                               Toast.makeText(this,"User Add Sucessfully",Toast.LENGTH_SHORT).show()

                           }
                       }



                   }
               }
           }
       }

    }








}
