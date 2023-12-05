package com.example.gethired

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.gethired.Token.TokenManager
import com.example.gethired.ViewModel.NotificationViewModel
import com.example.gethired.ViewModel.RegisterLoginViewModel
import com.example.gethired.entities.User
import com.example.gethired.entities.UserDto
import com.example.gethired.factory.NotificationViewModelFactory
import com.example.gethired.utils.Lists
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton

@SuppressLint("UseSwitchCompatOrMaterialCode")
class SettingActivity : AppCompatActivity() {

    private lateinit var notificationSwitch: Switch
    private lateinit var darkModeSwitch:Switch
    private lateinit var changePasswordBtn: CardView
    private lateinit var backButton:ImageView


    private lateinit var logoutBtn:CardView

    private lateinit var registerLoginViewModel: RegisterLoginViewModel

    private lateinit  var sharedPref: SharedPreferences
    private var currentUser: UserDto?=null

    private lateinit var notificationViewModel: NotificationViewModel
    private lateinit var tokenManager: TokenManager

    private lateinit  var notificationSharedPref: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        tokenManager=TokenManager(this)
        sharedPref = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        notificationSharedPref = getSharedPreferences(NOTIFICATION_PREF_FILE_NAME,Context.MODE_PRIVATE)
        notificationViewModel= ViewModelProvider(this, NotificationViewModelFactory(tokenManager))[NotificationViewModel::class.java]

        currentUser=CommonFunction.SharedPrefsUtil.fetchUserResponseFromSharedPreferences()

        registerLoginViewModel= ViewModelProvider(this)[RegisterLoginViewModel::class.java]

        backButton=findViewById(R.id.back_button)
        notificationSwitch=findViewById(R.id.setting_notification_switch)
        darkModeSwitch=findViewById(R.id.setting_darkMode_switch)
        changePasswordBtn=findViewById(R.id.setting_password_container)
        logoutBtn=findViewById(R.id.setting_logout_container)


        backButton.setOnClickListener {
            onBackPressed()
        }
        val isChecked=notificationSharedPref.getBoolean("all",true)
        if(notificationSharedPref.getBoolean("all",true)){

            ifUnChecked()
        }else{
            ifChecked()
        }
        notificationSwitch.setOnClickListener {

            if(notificationSwitch.isChecked){
                ifChecked()
                notificationViewModel.unMuteAllNotification(currentUser!!.id).observe(this){
                    if(!it) {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                        ifUnChecked()
                    }else{
                        ifChecked()
                        unMuteAll()
                    }
                }
            }
            if(!notificationSwitch.isChecked){
               ifUnChecked()
                notificationViewModel.muteAllNotification(currentUser!!.id).observe(this){
                    if(!it){
                        Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
                        ifChecked()
                    }else{
                        ifUnChecked()
                        muteAll()
                    }
                }
            }



        }

        darkModeSwitch.setOnClickListener {
            if(darkModeSwitch.isChecked){
                val editor = sharedPref.edit()
                editor.putString("dark_mode", "dark")
                editor.apply()
            }else{
                val editor = sharedPref.edit()
                editor.putString("dark_mode", "light")
                editor.apply()
            }
        }

        changePasswordBtn.setOnClickListener {
            val intent= Intent(this,ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        logoutBtn.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.logout_confirmation_popup, null)
            bottomSheetDialog.setContentView(view)

            bottomSheetDialog.show()
            val confirmButton:MaterialButton=view.findViewById(R.id.logout_confirmation_popup_logoutBtn)
            val cancelButton:MaterialButton=view.findViewById(R.id.logout_confirmation_popup_cancelBtn)

            confirmButton.setOnClickListener{

                registerLoginViewModel.logout(currentUser!!.username).observe(this){
                    if(it){
                        val editor = sharedPref.edit()
                        editor.clear()
                        editor.apply()


                        val intent=Intent(this,LoginActivity::class.java)
                        intent.putExtra("username",currentUser!!.username)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this,"Something went wrong!",Toast.LENGTH_SHORT).show()
                    }
                }

            }
            cancelButton.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

        }


    }

    @SuppressLint("CommitPrefEdits")
    private fun muteAll() {
        val editor=notificationSharedPref.edit()
        val notificationPref=Lists().notificationPref
        for(i in notificationPref){
            editor.putBoolean(i,false)
        }
        editor.putBoolean("all",false)
        editor.apply()

    }

    private fun unMuteAll() {
        val editor=notificationSharedPref.edit()
        val notificationPref=Lists().notificationPref
        for(i in notificationPref){
            editor.putBoolean(i,true)
        }
        editor.putBoolean("all",true)
        editor.apply()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun ifChecked(){
        notificationSwitch.isChecked=true
        notificationSwitch.backgroundTintList= ContextCompat.getColorStateList(this,R.color.on)
        notificationSwitch.trackTintList=ContextCompat.getColorStateList(this,R.color.on)
        notificationSwitch.thumbTintList=ContextCompat.getColorStateList(this,R.color.white)

    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun ifUnChecked() {
        notificationSwitch.isChecked=false
        notificationSwitch.backgroundTintList = ContextCompat.getColorStateList(this, R.color.off)
        notificationSwitch.trackTintList = ContextCompat.getColorStateList(this, R.color.off)
        notificationSwitch.thumbTintList = ContextCompat.getColorStateList(this, R.color.text)

    }
}