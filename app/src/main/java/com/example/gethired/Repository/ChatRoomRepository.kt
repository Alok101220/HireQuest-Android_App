package com.example.gethired.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gethired.Token.TokenManager
import com.example.gethired.api.RetrofitClient
import com.example.gethired.api.retrofitInterface.RetrofitInterfaceResumeAddUpdate
import com.example.gethired.entities.Chat
import com.example.gethired.entities.ChatRoom
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatRoomRepository(tokenManager: TokenManager) {
    private val retrofitAPI: RetrofitInterfaceResumeAddUpdate = RetrofitClient(tokenManager).getApiService()

    fun getChatList(userId:Long):MutableLiveData<List<ChatRoom>>{

        val allUsers=MutableLiveData<List<ChatRoom>>()

        val call : Call<List<ChatRoom>> = retrofitAPI.getAllChattingList(userId)

        call.enqueue(object : Callback<List<ChatRoom>>{
            override fun onResponse(
                call: Call<List<ChatRoom>>,
                response: Response<List<ChatRoom>>
            ) {
                if(response.isSuccessful){
                    if(response.body()!=null){
                        allUsers.value=response.body()
                    }
                }
            }

            override fun onFailure(call: Call<List<ChatRoom>>, t: Throwable) {

            }

        })

        return allUsers
    }

    fun sendChatRequest(senderId:Long, receiverId:Long, sampleMessage: Chat):LiveData<Boolean>{
        val isRequested=MutableLiveData<Boolean>()

        val call : Call<Boolean> = retrofitAPI.sendChatRequest(senderId, receiverId,sampleMessage)
        call.enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    isRequested.value=response.body()
                }else{
                    isRequested.value=false
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                isRequested.value=false
            }

        })
        return isRequested
    }

}