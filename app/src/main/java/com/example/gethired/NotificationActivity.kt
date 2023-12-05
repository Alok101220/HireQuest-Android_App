package com.example.gethired

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gethired.Token.TokenManager
import com.example.gethired.ViewModel.NotificationViewModel
//import com.example.gethired.adapter.ChatAdapter
import com.example.gethired.adapter.NotificationAdapter
import com.example.gethired.entities.Notification
import com.example.gethired.factory.NotificationViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog

class NotificationActivity : AppCompatActivity() {

    private lateinit var emptyText:TextView
    private lateinit var backButton:ImageView

    private var notificationList: MutableList<Notification> = mutableListOf()
    private lateinit var notificationRecyclerView: RecyclerView
    private lateinit var notificationAdapter: NotificationAdapter

    private lateinit var notificationViewModel: NotificationViewModel
    private lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        tokenManager = TokenManager(this)

        val user=CommonFunction.SharedPrefsUtil.fetchUserResponseFromSharedPreferences()

        notificationViewModel= ViewModelProvider(this, NotificationViewModelFactory(tokenManager))[NotificationViewModel::class.java]


        emptyText=findViewById(R.id.notification_empty_text)
        backButton=findViewById(R.id.backBtn)

        notificationRecyclerView=findViewById(R.id.notification_recyclerView)

        if(!::notificationAdapter.isInitialized){
            notificationAdapter= NotificationAdapter(notificationList,this)
            notificationRecyclerView.adapter=notificationAdapter
            notificationRecyclerView.layoutManager=LinearLayoutManager(this)
        }

        notificationViewModel.getAllNotification(user.id).observe(this){

            notificationList.clear()
            notificationList.addAll(it)


            if(notificationList.isEmpty()){
                emptyText.visibility= View.VISIBLE
            }else{
                notificationAdapter.updateData(notificationList)
            }

        }

        notificationAdapter.setOnEditIconClickListener(object :NotificationAdapter.OnEditIconClickListener{
            override fun onEditIconClick(position: Int) {

                val bottomSheetDialog = BottomSheetDialog(this@NotificationActivity)
                val view = layoutInflater.inflate(R.layout.notification_bottomsheet_popup, null)
                bottomSheetDialog.setContentView(view)

                bottomSheetDialog.show()



            }

        })
        backButton.setOnClickListener {
            onBackPressed()
        }
    }
}