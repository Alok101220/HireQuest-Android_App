package com.example.gethired

import android.content.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gethired.Callback.ChatCallBack
import com.example.gethired.Callback.UpdateUserCallback
import com.example.gethired.Room.MessageRoomDto
import com.example.gethired.Token.TokenManager
import com.example.gethired.ViewModel.ChatViewModel
import com.example.gethired.ViewModel.UserProfileViewModel
import com.example.gethired.ViewModel.UserViewModel
import com.example.gethired.adapter.MessageAdapter
import com.example.gethired.entities.Chat
import com.example.gethired.entities.ChattingUserInfo
import com.example.gethired.entities.User
import com.example.gethired.entities.UserDto
import com.example.gethired.factory.ChatViewModelFactory
import com.example.gethired.factory.UserProfileViewModelFactory
import com.example.gethired.factory.UserViewModelFactory
import com.example.gethired.websocket.WebSocketManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class ChattingActivity : AppCompatActivity() {
    private lateinit var pageHeadingUsername:TextView

    private lateinit var adapter: MessageAdapter
    private lateinit var sendMessageBtn:ImageView
    private lateinit var textInputLayout:TextInputLayout
    private lateinit var editText:TextInputEditText
    private lateinit var isUserOnline:TextView
    private lateinit var webSocket:WebSocket

    private lateinit var chattingRecyclerView: RecyclerView
    private lateinit var sendMessageContainer:LinearLayout
    private lateinit var acceptRequestContainer:LinearLayout
    private lateinit var requestSentContainer:LinearLayout

    private lateinit var sharedPref: SharedPreferences
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var userViewModel:UserViewModel

    private var messages = mutableListOf<Chat>()
    private lateinit var roomMessages:List<MessageRoomDto>
    private lateinit var tokenManager: TokenManager
    private var senderId=0
    private var receiverId=0
    private var receiverUser: UserDto? = null

    private var chattingUserInfo:ChattingUserInfo? =null

    var isServiceBound=false

    private var isChattingScreenVisible = false

    private val isChatLoaded :String ="chat_loading_pref_"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)



        registerReceiver(receiver, IntentFilter("NEW_MESSAGE_RECEIVED"))
        isChattingScreenVisible=true
        tokenManager = TokenManager(this@ChattingActivity)

        // Assuming you're inside the ChattingActivity
        val webSocketService = WebSocketManager.webSocketService
        if (webSocketService != null) {
            webSocket = webSocketService.webSocket
        }


        CommonFunction.SharedPrefsUtil.init(applicationContext)
        sharedPref = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        senderId = CommonFunction.SharedPrefsUtil.fetchUserResponseFromSharedPreferences().id.toInt()
        chatViewModel = ViewModelProvider(this, ChatViewModelFactory(tokenManager, this))[ChatViewModel::class.java]
        userViewModel = ViewModelProvider(
            this,
            UserViewModelFactory(tokenManager)
        )[UserViewModel::class.java]
//        get chatting partner/user

        // Check if URI is present
        val uri = intent.data
        if (uri != null) {
            // Handle data from URI
            val chatId = uri.lastPathSegment // Assuming chat ID is in the last path segment


            // Use chatId as needed
        } else {
            // Handle data from extras
            receiverId = intent.getIntExtra("user_id",senderId)





            // Use chatId as needed
        }

        val isChatLoadedFromBackend=sharedPref.getBoolean(isChatLoaded+receiverId,false)

        if(!isChatLoadedFromBackend){
            loadMessagesFromBackend()

            val editor = sharedPref.edit()
            editor.putBoolean(isChatLoaded+receiverId,false)
            editor.apply()
        }else{
            CoroutineScope(Dispatchers.Main).launch  {
//                val messageFromDb = chatViewModel.getMessagesFromDb(senderId.toLong(),receiverId.toLong())
//                messages.clear()
//                messages.addAll(messageFromDb.map { it.toChat() })
            }

//            fetching recent messages from backend

            val lastChatTimeStamp = LocalDateTime.parse(
                messages[messages.size-1].timestamp.subSequence(0,23),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
            )
            loadNewMessageFromBackend(lastChatTimeStamp)
        }

//        activity field initialization
        pageHeadingUsername=findViewById(R.id.page_heading_user_name)

        textInputLayout = findViewById(R.id.send_message_Layout)
        editText = findViewById(R.id.send_message_Edittext)
        sendMessageBtn = findViewById(R.id.send_message_Btn)
        isUserOnline=findViewById(R.id.page_heading_isUserOnline)
        chattingRecyclerView = findViewById(R.id.chatting_content_recyclerview)
        sendMessageContainer=findViewById(R.id.page_bottomContainer_message)
        requestSentContainer=findViewById(R.id.page_bottomContainer_send_request)
        acceptRequestContainer=findViewById(R.id.page_bottomContainer_accept_request)

        loadUser()


        chattingRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter=MessageAdapter(messages,senderId.toString(),chattingRecyclerView)
        chattingRecyclerView.adapter=adapter

// Set up send button click listener
        sendMessageBtn.setOnClickListener {
            val messageContent = editText.text.toString()
            if(messageContent.isNotEmpty()){
                sendMessage(messageContent)
            }

        }
        editText.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                textInputLayout.hint = ""
            } else {
                textInputLayout.hint = "Write a message"
            }
        }
    }

    private fun loadUser() {
//        userViewModel.getUser(tokenManager.getToken().toString(),object :UpdateUserCallback{
//            override fun onUserUpdated(updatedUserDto: UserDto) {
//                receiverUser=updatedUserDto
//                pageHeadingUsername.text = receiverUser!!.name
//            }
//
//            override fun onUpdateUserError() {
//
//            }
//
//        })
        chatViewModel.getUserInfo(senderId.toLong(),receiverId.toLong()).observe(this){
            if(it!=null){
                chattingUserInfo=it
            }
            pageHeadingUsername.text=chattingUserInfo!!.username
            if(chattingUserInfo!!.isRequested){

                sendMessageContainer.visibility=View.GONE

                if(chattingUserInfo!!.isSender){
                    acceptRequestContainer.visibility=View.GONE
                    requestSentContainer.visibility=View.VISIBLE
                }else{

                    acceptRequestContainer.visibility=View.VISIBLE
                    requestSentContainer.visibility=View.GONE
                }

            }else{
                sendMessageContainer.visibility=View.VISIBLE
                acceptRequestContainer.visibility=View.GONE
                requestSentContainer.visibility=View.GONE
            }
        }


    }

    private fun loadNewMessageFromBackend(timeStamp:LocalDateTime) {
        val formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") // Define your desired format

        val formattedDateTime: String = timeStamp.format(formatter)
        CoroutineScope(Dispatchers.Main).launch {
            chatViewModel.getAllChat(senderId.toLong(),receiverId.toLong(),formattedDateTime,object :ChatCallBack{
                override fun onChatResponse(chats: List<Chat>) {
                    messages.clear()
                    messages.addAll(chats)
                    adapter.notifyDataSetChanged()
                    chattingRecyclerView.scrollToPosition(messages.size - 1)
                }
                override fun onChatError() {

                }

            })
        }
    }

    private fun loadMessagesFromBackend() {
        val dateTime = LocalDateTime.of(2000, 4, 20, 0, 0)
        val formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") // Define your desired format

        val formattedDateTime: String = dateTime.format(formatter)
        CoroutineScope(Dispatchers.Main).launch {
            chatViewModel.getAllChat(senderId.toLong(),receiverId.toLong(),
                formattedDateTime,object :ChatCallBack{
                    override fun onChatResponse(chats: List<Chat>) {
                        messages.addAll(chats)
                        adapter.notifyDataSetChanged()
                        chattingRecyclerView.scrollToPosition(messages.size - 1)
                    }

                    override fun onChatError() {
                        messages.clear()
                    }

                })
        }
    }

    private fun sendMessage(content: String) {

        val messageData = Chat(0,senderId.toLong(),receiverId.toLong(),content, LocalDateTime.now().toString(),false)
        val messageJson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeSerializer())
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
            .create()
            .toJson(messageData)
        if(webSocket.send(messageJson)){
            // Add the message to your UI
            messages.add(messageData)
            adapter.notifyItemInserted(messages.size - 1)
            chattingRecyclerView.scrollToPosition(messages.size - 1)

            // Clear the message input field
            editText.text?.clear()

//            inserting message to db
            CoroutineScope(Dispatchers.Main).launch {
//                chatViewModel.insertMessageToRoom(messageData.toMessageRoomDto())
            }

        }else{
            Toast.makeText(this,"Unable to send message!", Toast.LENGTH_SHORT).show()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun MessageRoomDto.toChat(): Chat {
        return Chat(
            // Map the properties from MessageRoomDto to Chat
            id = this.id,
            senderId = this.senderId,
            receiverId = this.receiverId,
            content = this.content!!,
            timestamp= this.timeStamp.toString(),
            seen=this.seen
        )
    }
    private fun Chat.toMessageRoomDto(): MessageRoomDto {
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
//        val dateTime = LocalDateTime.parse(messageData.get("timeStamp") as String, formatter)

        val chatTimeStamp = LocalDateTime.parse(this.timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))
        return MessageRoomDto(
            // Map the properties from Chat to MessageRoomDto

            id = this.id,
            senderId = this.senderId,
            receiverId = this.receiverId,
            content = this.content,
            timeStamp = chatTimeStamp,
            seen = this.seen
        )
    }

    override fun onResume() {
        super.onResume()
        isVisible = true
        adapter.startUpdatingTimestamps()

    }


    override fun onPause() {
        super.onPause()
        isVisible = false
        adapter.startUpdatingTimestamps()
    }

    override fun onDestroy() {
        super.onDestroy()
        isVisible = false
        unregisterReceiver(receiver)
    }



    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "NEW_MESSAGE_RECEIVED") {
                val messageJson = intent.getStringExtra("message")

                // Convert the JSON message to a Chat object
                val messageData = Gson().fromJson(messageJson, Chat::class.java)

                // Create a Chat object
                val message = Chat(
                    id = 0,
                    senderId = messageData.senderId,
                    receiverId = messageData.receiverId,
                    content = messageData.content,
                    timestamp = messageData.timestamp,
                    seen = false
                )

                // Add the message to the list
                messages.add(messageData)

                // Notify the adapter that a new message has been added
                adapter.notifyItemInserted(messages.size - 1)

                // Scroll to the newly added message
                chattingRecyclerView.scrollToPosition(messages.size - 1)

                // If the chatting screen is not visible, send a notification

            }
        }
    }

    companion object {
        var isVisible = false
    }


}