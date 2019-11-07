package com.example.smaker.utilities

import android.content.Context
import android.content.SharedPreferences
import com.android.volley.toolbox.Volley

class SharedPrefs(context: Context) {
    val PREFS_FILENAME ="prefs"
    val perfs:SharedPreferences =context.getSharedPreferences(PREFS_FILENAME,0)

    val IS_LOGGED_IN ="isLoggedIn"
    val AUTH_TOKEN   ="authToken"
    val USER_EMAIL   ="userEmail"


  var isLoggedIn:Boolean
    get() = perfs.getBoolean(IS_LOGGED_IN,false)
    set(value) = perfs.edit().putBoolean(IS_LOGGED_IN,value).apply()

    var authtoken:String
        get() = perfs.getString(AUTH_TOKEN,"").toString()
        set(value) = perfs.edit().putString(AUTH_TOKEN,value).apply()

    var useremail:String
        get() = perfs.getString(USER_EMAIL,"").toString()
        set(value) = perfs.edit().putString(USER_EMAIL,value).apply()


    val requestQueue = Volley.newRequestQueue(context)












}