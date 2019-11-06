package com.example.smaker.controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.smaker.Model.Channel
import com.example.smaker.R
import com.example.smaker.services.AuthService
import com.example.smaker.services.MessageService
import com.example.smaker.services.UserDataService
import com.example.smaker.utilities.BROADCAST_USER_DATA_CHANGE
import com.example.smaker.utilities.SOCKET_URL
import io.socket.client.IO
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val socket= IO.socket(SOCKET_URL)
    lateinit var  channeladapter:ArrayAdapter<Channel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        socket.connect()
        socket.on("channelCreated",onNewChannel)


        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )



        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        LocalBroadcastManager.getInstance(this).registerReceiver(user_data_change, IntentFilter(
            BROADCAST_USER_DATA_CHANGE))

        setupAdapters()

    }


    private  fun setupAdapters(){
        channeladapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,MessageService.channels)
        channelslist.adapter=channeladapter
    }


    private  fun getchannelListFromApi(){
        MessageService.get_channels(this){
            if(it){
                channeladapter.notifyDataSetChanged()
            }
        }
    }





    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(user_data_change,
            IntentFilter(BROADCAST_USER_DATA_CHANGE))

    }


    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(user_data_change)
        socket.disconnect()
    }



  private  val user_data_change= object : BroadcastReceiver(){
      override fun onReceive(context: Context?, intent: Intent?) {
       if (AuthService.isLoggedIn){
           ProfileUserName.text=UserDataService.name
           ProfileUserEmail.text=UserDataService.email
           ProfileLogIn_.text="LogOut"
           val resourceId=resources.getIdentifier(UserDataService.avatarName,"drawable",packageName)
           ProfileUserImage.setImageResource(resourceId)
           ProfileUserImageBackGround.setBackgroundColor(UserDataService.returnAvatarColor(UserDataService.avatarColor))
           getchannelListFromApi()
       }}}

    //Functions  User Clicked
    fun LoginNavClicked(view:View){
        if(AuthService.isLoggedIn){
            UserDataService.logout()
            ProfileUserName.text="LogIn"
            ProfileUserEmail.text=""
            ProfileLogIn_.text="LogIn"
            ProfileUserImage.setImageResource(R.drawable.boss)
            ProfileUserImageBackGround.setBackgroundColor(Color.TRANSPARENT)


        }else{
            val LogInIntent= Intent(this, LoginActivity::class.java)
            startActivity(LogInIntent)


        }


    }


    fun AddChannelsIntoList(view:View){
        
        
        if(AuthService.isLoggedIn){
            val builder= AlertDialog.Builder(this)
            val dialogview=layoutInflater.inflate(R.layout.add_channel_dialog,null)
            builder.setView(dialogview)
                .setPositiveButton("add"){
                    dialogInterface, i ->
                    val channelnametextfield= dialogview.findViewById<EditText>(R.id.Channelnameedt)
                    val channelDescritiontextfield= dialogview.findViewById<EditText>(R.id.ChannelDescription)
                    val channelname=channelnametextfield.text.toString()
                    val channelDescription=channelDescritiontextfield.text.toString()
                    socket.emit("newChannel",channelname,channelDescription)


                    
                }
                .setNegativeButton("Cancel"){
                    dialogInterface, i ->
                }
                .show()
        }
    }


    private val onNewChannel = Emitter.Listener {
        args -> runOnUiThread {

        val chname=args[0] as String
        val chdes=args[1] as String
        val chid=args[2] as String
        val newChannel = Channel(chname,chdes,chid)
        MessageService.channels.add(newChannel)
        channeladapter.notifyDataSetChanged()

    }
    }






    fun hideKeyboard(){
        val inputManager=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(inputManager.isAcceptingText){
            inputManager.hideSoftInputFromWindow(currentFocus?.windowToken,0)
        }

    }






}
