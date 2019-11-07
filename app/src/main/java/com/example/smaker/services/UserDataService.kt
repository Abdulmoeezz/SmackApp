package com.example.smaker.services

import android.graphics.Color
import android.util.Log
import com.example.smaker.controller.App
import java.util.*
import kotlin.math.log

object UserDataService {

    var id =""
    var name=""
    var avatarColor=""
    var email=""
    var avatarName=""


    fun logout(){
        id=""
        name=""
        avatarName=""
        email=""
        avatarColor=""
        App.sharedPreferences.authtoken=""
        App.sharedPreferences.useremail=""
        App.sharedPreferences.isLoggedIn=false
        MessageService.ClearMessages()
        MessageService.ClearChannels()


    }



    fun returnAvatarColor(components:String):Int{
        val strippedcolor=components.replace("[","").replace("]","").replace(",","")
        var r= 0
        var g=0
        var b=0

        val scanner=Scanner(strippedcolor)
        if(scanner.hasNext()){
            try {
                r = (scanner.nextDouble() * 255).toInt()
                g = (scanner.nextDouble() * 255).toInt()
                b = (scanner.nextDouble() * 255).toInt()
            }catch (e:InputMismatchException){
                Log.d("Error","${e.localizedMessage}")
            }
        }


        return Color.rgb(r,g,b)

    }





}