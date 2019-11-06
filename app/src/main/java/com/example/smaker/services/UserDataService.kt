package com.example.smaker.services

import android.graphics.Color
import android.util.Log
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
        AuthService.authToken=""
        AuthService.user=""
        AuthService.isLoggedIn=false


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