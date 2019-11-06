package com.example.smaker.services

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.smaker.Model.Channel
import com.example.smaker.utilities.URL_GET_CHANNELS
import org.json.JSONException

object MessageService {

    val channels=ArrayList<Channel>()

    fun get_channels(context: Context,complete:(Boolean)->Unit){

        val changerequest= object: JsonArrayRequest(
            Method.GET,
            URL_GET_CHANNELS,
            null,
            Response.Listener {

                try{
                    for (x in 0 until it.length()){
                        val ch=it.getJSONObject(x)
                        val name=ch.getString("name")
                        val description=ch.getString("description")
                        val id=ch.getString("_id")

                        val newchannel=Channel(name = name,description = description, id = id)
                        this.channels.add(newchannel)
                    }

                    complete(true)
                }
                catch (e:JSONException){
                    complete(false)

                }


            },
            Response.ErrorListener {
                complete(false)
            })
        {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers= HashMap<String,String>()
                headers.put("Authorization","Bearer ${AuthService.authToken}")
                return headers }
        }

Volley.newRequestQueue(context).add(changerequest)

    }






}