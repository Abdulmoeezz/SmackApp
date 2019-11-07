package com.example.smaker.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.smaker.Model.Message
import com.example.smaker.R
import com.example.smaker.services.UserDataService
import kotlinx.android.synthetic.main.item_message.*

class MessageAdapter( val context:Context,val messages:ArrayList<Message>):Adapter<MessageAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(context).inflate(R.layout.item_message,parent,false )
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return  messages.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMessage(context,messages[position])

    }


    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val userimage = itemView.findViewById<ImageView>(R.id.item_profileimage)
        val username = itemView.findViewById<TextView>(R.id.item_profileuserName)
        val usermessage = itemView.findViewById<TextView>(R.id.item_profileMessage)
        val timestamp = itemView.findViewById<TextView>(R.id.item_MessageTime)
        val viewcolor = itemView.findViewById<View>(R.id.item_MessageAvatarColor)


        fun bindMessage(context: Context,message: Message){
            val resourceID =context.resources.getIdentifier(message.userAvatar,"drawable",context.packageName)
            userimage.setImageResource(resourceID)
            username.text=message.userName
            usermessage.text=message.message
            timestamp.text=message.timeStamp
            viewcolor.setBackgroundColor(UserDataService.returnAvatarColor(message.userAvatarColor)) }

    }

}