package com.example.smaker.services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smaker.utilities.URL_CREATE_USER
import com.example.smaker.utilities.URL_LOGIN
import com.example.smaker.utilities.URL_REGISTER
import org.json.JSONException
import org.json.JSONObject
import kotlin.math.log

object AuthService {
    var isLoggedIn:Boolean = false;
    var user:String=""
    var authToken:String=""


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

    Volley.newRequestQueue(context).add(registerRequest)
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
                user=it.getString("user")
                authToken=it.getString("token")
                isLoggedIn=true
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

    Volley.newRequestQueue(context).add(loginRequest)
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
            headers.put("Authorization","Bearer $authToken")
            return  headers
        }

    }

    Volley.newRequestQueue(context).add(createRequest)

}

}