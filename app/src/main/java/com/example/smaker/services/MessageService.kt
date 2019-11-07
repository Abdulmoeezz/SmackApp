package com.example.smaker.services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.smaker.Model.Channel
import com.example.smaker.Model.Message
import com.example.smaker.controller.App
import com.example.smaker.utilities.URL_GET_CHANNELS
import com.example.smaker.utilities.URL_GET_MESSAGES
import org.json.JSONException

object MessageService {

    val channels=ArrayList<Channel>()
    val messages=ArrayList<Message>()

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
                headers.put("Authorization","Bearer ${App.sharedPreferences.authtoken}")
                return headers }
        }

        App.sharedPreferences.requestQueue.add(changerequest)

    }

    fun get_messages(channelId:String, complete: (Boolean) -> Unit){
        val url= "$URL_GET_MESSAGES$channelId"

        val messagerequest=object :JsonArrayRequest(Method.GET,url,null,
            Response.Listener {
                ClearMessages()
        try{
            for(x in 0 until  it.length()){
                val message=it.getJSONObject(x)
                val messagebody=message.getString("messageBody")
                val channelid=message.getString("channelId")
                val id=message.getString("_id")
                val userName=message.getString("userName")
                val userAvatar=message.getString("userAvatar")
                val userAvatarColor=message.getString("userAvatarColor")
                val timestamp=message.getString("timeStamp")

                val newMsg=Message(messagebody,userName,channelid,userAvatar,userAvatarColor,id,timestamp)
                this.messages.add(newMsg)
            }
            complete(true)


        }catch (e:JSONException){ complete(false)}}
            ,Response.ErrorListener {
                complete(false)
                Log.e("Error","${it.localizedMessage}")
            })
        {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers= HashMap<String,String>()
                headers.put("Authorization","Bearer ${App.sharedPreferences.authtoken}")
                return headers }

        }


        App.sharedPreferences.requestQueue.add(messagerequest)


    }
    fun ClearMessages(){
        messages.clear()
    }

    fun ClearChannels(){
        channels.clear()
    }






}