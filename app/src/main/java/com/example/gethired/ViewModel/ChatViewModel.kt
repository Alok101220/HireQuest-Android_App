package com.example.gethired.ViewModel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gethired.Callback.ChatCallBack
import com.example.gethired.Repository.ChatRepository
import com.example.gethired.Room.MessageRoomDto
import com.example.gethired.Token.TokenManager
import com.example.gethired.entities.Chat
import com.example.gethired.entities.ChattingUserInfo
import java.sql.Timestamp
import java.time.LocalDateTime

class ChatViewModel(
    tokenManager: TokenManager,
    context: Context
) : ViewModel() {
    private val chatRepository=ChatRepository(tokenManager,context)

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAllChat(senderId: Long, receiverId: Long, timeStamp: String,callback: ChatCallBack) {
        chatRepository.getAllChat(senderId, receiverId,timeStamp, object : ChatCallBack {
            override fun onChatResponse(chats: List<Chat>) {
                callback.onChatResponse(chats)
            }

            override fun onChatError() {
                callback.onChatError()
            }
        })
    }

    fun updateMessage(senderId: Long,receiverId: Long,chat: Chat): MutableLiveData<Chat> {
        return chatRepository.updateMessage(senderId,receiverId,chat)
    }
//
//    suspend fun getMessagesFromDb(senderUsername: Long, receiverUsername: Long): List<MessageRoomDto> {
//        return chatRepository.getMessagesFromDb(senderUsername, receiverUsername)
//    }
//
//    suspend fun insertAllMessagesToRoom(roomMessages: List<MessageRoomDto>) {
//        chatRepository.insertAllMessagesToRoom(roomMessages)
//    }
//
//    suspend fun insertMessageToRoom(messageRoomDto: MessageRoomDto) {
//        chatRepository.insertMessageToRoom(messageRoomDto)
//    }
//
//    suspend fun deleteMessageFromRoom(messageRoomDto: MessageRoomDto) {
//        chatRepository.deleteMessageFromRoom(messageRoomDto)
//    }

    fun getUserInfo(senderId: Long,receiverId: Long):LiveData<ChattingUserInfo>{
        return chatRepository.getUserInfo(senderId,receiverId)
    }
}
