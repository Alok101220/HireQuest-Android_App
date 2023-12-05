package com.example.gethired.Repository

import androidx.lifecycle.MutableLiveData
import com.example.gethired.Callback.MeetingCallback
import com.example.gethired.Token.TokenManager
import com.example.gethired.api.RetrofitClient
import com.example.gethired.api.retrofitInterface.RetrofitInterfaceResumeAddUpdate
import com.example.gethired.entities.Meeting
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MeetingRepository(tokenManager: TokenManager) {
    private val retrofitAPI: RetrofitInterfaceResumeAddUpdate = RetrofitClient(tokenManager).getApiService()

    fun createMeeting(meeting: Meeting,callback:MeetingCallback){
        val call: Call<Meeting> = retrofitAPI.createMeeting(meeting)

        call.enqueue(object :Callback<Meeting>{
            override fun onResponse(call: Call<Meeting>, response: Response<Meeting>) {
                if(response.isSuccessful){
                    if(response.body()!=null){
                        callback.onMeetingResponse(response.body()!!)
                    }else{
                        callback.onMeetingError("Meeting already exist!")
                    }

                }else{
                    callback.onMeetingError("Meeting already exist!")
                }
            }

            override fun onFailure(call: Call<Meeting>, t: Throwable) {
                callback.onMeetingError("Error")
            }

        })
    }

    fun updateMeeting(user:String, hr:String,meeting: Meeting,callback:MeetingCallback){
        val call: Call<Meeting> = retrofitAPI.updateMeeting(user,hr,meeting)

        call.enqueue(object :Callback<Meeting>{
            override fun onResponse(call: Call<Meeting>, response: Response<Meeting>) {
                if(response.isSuccessful){
                    callback.onMeetingResponse(response.body()!!)
                }else{
                    callback.onMeetingError("Error")
                }
            }

            override fun onFailure(call: Call<Meeting>, t: Throwable) {
                callback.onMeetingError("Error")
            }

        })
    }


    fun getAllPastMeeting(user:String):MutableLiveData<List<Meeting>>{
        val meetings = MutableLiveData<List<Meeting>> ()

        val call: Call<List<Meeting>> =retrofitAPI.getAllPastMeeting(user)
        call.enqueue(object :Callback<List<Meeting>>{
            override fun onResponse(call: Call<List<Meeting>>, response: Response<List<Meeting>>) {
                if(response.isSuccessful){
                    if (response.body()!=null){
                        meetings.value=response.body()
                    }else{
                        meetings.value= mutableListOf()
                    }
                }
            }

            override fun onFailure(call: Call<List<Meeting>>, t: Throwable) {
                meetings.value= mutableListOf()
            }

        })
        return meetings
    }
    fun getAllMeeting(user:String):MutableLiveData<List<Meeting>>{
        val meetings = MutableLiveData<List<Meeting>> ()

        val call: Call<List<Meeting>> =retrofitAPI.getAllMeeting(user)
        call.enqueue(object :Callback<List<Meeting>>{
            override fun onResponse(call: Call<List<Meeting>>, response: Response<List<Meeting>>) {
                if(response.isSuccessful){
                    if (response.body()!=null){
                        meetings.value=response.body()
                    }else{
                        meetings.value= mutableListOf()
                    }
                }
            }

            override fun onFailure(call: Call<List<Meeting>>, t: Throwable) {
                meetings.value= mutableListOf()
            }

        })
        return meetings
    }

}