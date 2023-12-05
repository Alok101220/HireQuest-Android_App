package com.example.gethired.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.gethired.ChattingActivity
import com.example.gethired.CommonFunction
import com.example.gethired.R
import com.example.gethired.Token.TokenManager
import com.example.gethired.ViewModel.ChatRoomViewModel
import com.example.gethired.ViewModel.ChatViewModel
import com.example.gethired.ViewModel.UserViewModel
import com.example.gethired.adapter.ChatAdapter
import com.example.gethired.entities.ChatRoom
import com.example.gethired.entities.User
import com.example.gethired.entities.UserDto
import com.example.gethired.factory.ChatRoomViewModelFactory
import com.example.gethired.factory.ChatViewModelFactory
import com.example.gethired.factory.UserViewModelFactory

class ChatFragment : Fragment() {

    private var chatRoomList: MutableList<ChatRoom> = mutableListOf()
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var chatRoomAdapter: ChatAdapter
    private lateinit var noChatFound: LinearLayout
    private lateinit var chatFragmentBody:LinearLayout
    private lateinit var loadingChatList: LottieAnimationView


    private lateinit var chatViewModel: ChatViewModel
    private lateinit var tokenManager: TokenManager

    private lateinit var chatRoomViewModel: ChatRoomViewModel

    private var currentUser: UserDto?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootVIew=inflater.inflate(R.layout.fragment_chat, container, false)

        tokenManager= TokenManager(requireContext())

        CommonFunction.SharedPrefsUtil.init(requireContext())
        currentUser=CommonFunction.SharedPrefsUtil.fetchUserResponseFromSharedPreferences()
        chatRoomViewModel = ViewModelProvider(this, ChatRoomViewModelFactory(tokenManager))[ChatRoomViewModel::class.java]
//        chatViewModel = ViewModelProvider(this,ChatViewModelFactory(tokenManager,requireContext()))[ChatViewModel::class.java]


        chatRecyclerView=rootVIew.findViewById(R.id.chat_recyclerView)
        noChatFound=rootVIew.findViewById(R.id.chat_not_found)
        chatFragmentBody=rootVIew.findViewById(R.id.chat_fragment_body)
        loadingChatList=rootVIew.findViewById(R.id.loadingChat)

        loadingChatList.playAnimation()
        loadingChatList.visibility=View.VISIBLE

        chatRoomAdapter= ChatAdapter(chatRoomList,requireContext())
        chatRecyclerView.adapter=chatRoomAdapter
        chatRecyclerView.layoutManager=LinearLayoutManager(requireContext())

        chatRoomViewModel.getAllChattingUsers(currentUser!!.id).observe(requireActivity()){
            chatRoomList.clear()
            chatRoomList.addAll(it)
            chatRoomAdapter.update(chatRoomList)
            if(it.isEmpty()){
                noChatFound.visibility=View.VISIBLE
                chatRecyclerView.visibility=View.GONE
                loadingChatList.visibility=View.GONE
            }else{
                noChatFound.visibility=View.GONE
                chatRecyclerView.visibility=View.VISIBLE
                loadingChatList.visibility=View.GONE
            }

        }

        chatRoomAdapter.setOnItemClickListener(object : ChatAdapter.OnItemClickListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClick(position: Int) {
                val intent=Intent(requireContext(),ChattingActivity::class.java)
                intent.putExtra("user_id",chatRoomList[position].receiver.id.toInt())
                intent.putExtra("receiver",chatRoomList[position].receiver)
                startActivity(intent)
            }
        })
        return rootVIew
    }


}