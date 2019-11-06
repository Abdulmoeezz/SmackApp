package com.example.smaker.controller

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.smaker.R
import com.example.smaker.services.AuthService
import com.example.smaker.utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_log_up.*
import java.util.*


class LogUpActivity : AppCompatActivity() {


    var userAvatar ="profileDefault"
    var avatarColor ="[0.5,0.5,0.5,1]"
    val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_up)
        Spinner_.visibility=View.INVISIBLE
    }


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
        val email    =  name.text.toString()
        val password =  password_.text.toString()
        val username =  Email_.text.toString()

if(!username.isNullOrEmpty() && !password.isNullOrEmpty()&& !email.isNullOrEmpty()){
    enableSpinner(true)
    AuthService.resgisterUser(this,email,password){
        if(it){
            //resgisterSucess
            AuthService.loginUser(this,email,password){
                //LoginSucess
                if(it){
                    AuthService.createUser(this,username,email,userAvatar,avatarColor){
                        if(it){
                            val userdatachanged=Intent(BROADCAST_USER_DATA_CHANGE)
                            LocalBroadcastManager.getInstance(this).sendBroadcast(userdatachanged)
                            enableSpinner(false)
                            name.text.clear()
                            password_.text.clear()
                            Email_.text.clear()
                            finish()
                        }else
                        {
                            errorToast()
                        }
                    }//CreateUser
                }else
                {
                    errorToast()
                }
            }//LoginUser
        }else
        {
            errorToast()
        }

    }//Resgister User
}else{
    Toast.makeText(this,"make sure all details are filled in.",Toast.LENGTH_LONG).show()
    enableSpinner(false)
}
    }

    fun enableSpinner(enable:Boolean){
        if(enable){
            Spinner_.visibility=View.VISIBLE

        }else{
            Spinner_.visibility=View.INVISIBLE

        }
        ChangeAvatarImage_.isEnabled=!enable
        GenerateBackgroundColor.isEnabled=!enable
        AvatarImages.isEnabled=!enable

    }
    fun errorToast(){
        Toast.makeText(this,"Something went wrong,please try again.",Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }








}
