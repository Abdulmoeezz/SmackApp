package com.example.smaker.services

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smaker.controller.App
import com.example.smaker.utilities.*
import org.json.JSONException
import org.json.JSONObject
import kotlin.math.log

object AuthService {
   /* var isLoggedIn:Boolean = false;
    var user:String=""
    var authToken:String=""*/


fun resgisterUser(context:Context, email:String,password:String,complete:(Boolean)->Unit) {

    val jsonBody = JSONObject()
    jsonBody.put("email",email)
    jsonBody.put("password",password)
    val requestBody=jsonBody.toString()
    val registerRequest=object : StringRequest(
        Method.POST,
        URL_REGISTER,
        Response.Listener {
            println(it)
            Log.e("Error","$it")
            complete(true)
        },
        Response.ErrorListener {
            Log.e("Error","Could Not Register Request:# $it")
            complete(false)
        }) {
        override fun getBodyContentType(): String {
            return "application/json; charset=utf-8"
        }

        override fun getBody(): ByteArray {
            return requestBody.toByteArray()
        } }

   App.sharedPreferences.requestQueue.add(registerRequest)
}

fun loginUser(context: Context,email: String,password: String,complete: (Boolean) -> Unit){

    val jsonBody = JSONObject()
    jsonBody.put("email",email)
    jsonBody.put("password",password)
    val requestBody=jsonBody.toString()

    val loginRequest= object : JsonObjectRequest(
        Method.POST,
        URL_LOGIN,
        null,
        Response.Listener
        {
            try{
               App.sharedPreferences.useremail =it.getString("user")
                App.sharedPreferences.authtoken=it.getString("token")
                App.sharedPreferences.isLoggedIn=true
                Log.e("Error","SucessFully Login : $it")
                complete(true)
            }catch (e:JSONException){
                Log.e("Error","Error Login: ${e.localizedMessage}")
                complete(false)
            }


        },
        Response.ErrorListener
        {
            Log.e("Error","Could Not Login Request:# $it")
            complete(false)

        }){
        override fun getBodyContentType(): String {
            return "application/json; charset=utf-8"
        }

        override fun getBody(): ByteArray {
            return requestBody.toByteArray()
        }
        }

    App.sharedPreferences.requestQueue.add(loginRequest)
}

fun createUser(context: Context,name:String,email:String,avatarName:String,avatarColor:String,complete: (Boolean) -> Unit){

    val jsonBody = JSONObject()
    jsonBody.put("email",email)
    jsonBody.put("avatarName",avatarName)
    jsonBody.put("avatarColor",avatarColor)
    jsonBody.put("name",name)
    val requestBody=jsonBody.toString()

    val createRequest = object : JsonObjectRequest(
        Method.POST,
        URL_CREATE_USER,
        null,
        Response.Listener
        {
            try {
                UserDataService.name=it.getString("name")
                UserDataService.avatarColor==it.getString("avatarColor")
                UserDataService.avatarName=it.getString("avatarName")
                UserDataService.email=it.getString("email")
                UserDataService.id=it.getString("_id")
                complete(true)

            }catch (e:JSONException){
                Log.e("Error","${e.localizedMessage}")
                complete(false)

            }

        },
        Response.ErrorListener
        {
            Log.e("Error","Could Not Add user:# $it")
            complete(false)

        }){
        override fun getBodyContentType(): String {
            return "application/json; charset=utf-8"
        }

        override fun getBody(): ByteArray {
            return requestBody.toByteArray()
        }

        override fun getHeaders(): MutableMap<String, String> {
            val headers=HashMap<String,String>()
            headers.put("Authorization","Bearer ${App.sharedPreferences.authtoken}")
            return  headers
        }

    }

    App.sharedPreferences.requestQueue.add(createRequest)

}

fun findUserByEmail(context: Context,complete: (Boolean) -> Unit){
    val findUserRequest = object:JsonObjectRequest(Method.GET,"$URL_GET_USER_EMAIL${App.sharedPreferences.useremail}",null,Response
        .Listener {
            try{
                UserDataService.email=it.getString("email")
                UserDataService.name=it.getString("name")
                UserDataService.avatarColor=it.getString("avatarColor")
                UserDataService.avatarName=it.getString("avatarName")
                UserDataService.id=it.getString("_id")
                val userDataChange=Intent(BROADCAST_USER_DATA_CHANGE)
                LocalBroadcastManager.getInstance(context).sendBroadcast(userDataChange)


                Toast.makeText(context,"Yes Got the User!",Toast.LENGTH_SHORT).show()
                complete(true)
            }catch (e:JSONException){
                complete(false)
            }

        },
        Response.ErrorListener {
            Log.e("Error","${it.localizedMessage}")
            Toast.makeText(context,"Could not Find User!",Toast.LENGTH_SHORT).show()
            complete(false)
        }

    ){

        override fun getBodyContentType(): String {
            return "application/json; charset=utf-8"
        }
        override fun getHeaders(): MutableMap<String, String> {
            val headers=HashMap<String,String>()
            headers.put("Authorization","Bearer ${App.sharedPreferences.authtoken}")
            return  headers
        }
    }

    App.sharedPreferences.requestQueue.add(findUserRequest)
}



}