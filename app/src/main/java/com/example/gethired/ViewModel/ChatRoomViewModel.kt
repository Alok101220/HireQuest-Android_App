package com.example.gethired.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.gethired.Repository.ChatRoomRepository
import com.example.gethired.Token.TokenManager
import com.example.gethired.entities.Chat
import com.example.gethired.entities.ChatRoom

class ChatRoomViewModel(tokenManager: TokenManager) :ViewModel(){

    val chatRoomRepository=ChatRoomRepository(tokenManager)

    fun getAllChattingUsers(userId:Long):LiveData<List<ChatRoom>>{
        return chatRoomRepository.getChatList(userId)
    }
    fun sendChatRequest(senderId:Long, receiverId:Long,sampleMessage: Chat):LiveData<Boolean>{
        return chatRoomRepository.sendChatRequest(senderId,receiverId,sampleMessage)
    }
}