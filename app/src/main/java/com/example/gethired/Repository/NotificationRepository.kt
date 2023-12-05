package com.example.gethired.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gethired.Callback.NotificationCallback
import com.example.gethired.Token.TokenManager
import com.example.gethired.api.RetrofitClient
import com.example.gethired.api.retrofitInterface.RetrofitInterfaceResumeAddUpdate
import com.example.gethired.entities.Notification
import com.example.gethired.entities.NotificationPreference
import com.example.gethired.entities.NotificationRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationRepository(tokenManager: TokenManager) {

    private val retrofitAPI: RetrofitInterfaceResumeAddUpdate = RetrofitClient(tokenManager).getApiService()

    fun sendNotification(request: NotificationRequest){

        val call: Call<String> = retrofitAPI.sendNotification(request)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

                if(response.isSuccessful){
                    if(response.body()!=null){
                        val name=response.body()
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("send-message",t.toString())
            }

        })
    }

    fun updateNotification(notificationId:Long, callback:NotificationCallback){
        val call :Call<Notification> = retrofitAPI.updateNotification(notificationId)

        call.enqueue(object :Callback<Notification>{
            override fun onResponse(call: Call<Notification>, response: Response<Notification>) {
                if(response.isSuccessful){
                    if (response.body()!=null){
                        callback.onNotificationResponse(response.body()!!)
                    }else{
                        callback.onNotificationError()
                    }
                }
            }

            override fun onFailure(call: Call<Notification>, t: Throwable) {
               callback.onNotificationError()
            }

        })
    }

    fun deleteNotification(notificationId: Long){
        val call :Call<String> =retrofitAPI.deleteNotification(notificationId)
        call.enqueue(
            object :Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {

                }

                override fun onFailure(call: Call<String>, t: Throwable) {

                }

            }
        )
    }

    fun getAllNotification(userId:Long):LiveData<List<Notification>>{
        val notifications=MutableLiveData<List<Notification>> ()

        val call :Call<List<Notification>> = retrofitAPI.getNotification(userId)

        call.enqueue(object :Callback<List<Notification>>{
            override fun onResponse(
                call: Call<List<Notification>>,
                response: Response<List<Notification>>
            ) {
                if(response.isSuccessful){
                    if(response.body()!=null){
                        notifications.value=response.body()
                    }else{
                        notifications.value= mutableListOf()
                    }
                }
            }

            override fun onFailure(call: Call<List<Notification>>, t: Throwable) {
                notifications.value= mutableListOf()
            }

        })
        return notifications
    }

    fun getAllNotificationPreference(userId: Long):LiveData<List<NotificationPreference>>{
        val notificationPreferences=MutableLiveData<List<NotificationPreference>> ()
        val call :Call<List<NotificationPreference>> = retrofitAPI.getNotificationPreference(userId)

        call.enqueue(object :Callback<List<NotificationPreference>>{
            override fun onResponse(
                call: Call<List<NotificationPreference>>,
                response: Response<List<NotificationPreference>>
            ) {
                if(response.isSuccessful){
                    if(response.body()!=null){
                        notificationPreferences.value=response.body()
                    }else{
                        notificationPreferences.value= mutableListOf()
                    }
                }
            }

            override fun onFailure(call: Call<List<NotificationPreference>>, t: Throwable) {
                notificationPreferences.value= mutableListOf()
            }

        })
        return notificationPreferences
    }

    fun updateNotificationPreference(notificationType:String, userId: Long):LiveData<Boolean>{
        val isUpdated=MutableLiveData<Boolean> ()
        val call :Call<Boolean> =retrofitAPI.updateNotificationPreference(notificationType,userId)
        call.enqueue(object :Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    isUpdated.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                isUpdated.value=false
            }

        })
        return isUpdated
    }

    fun muteAllNotification(userId: Long):LiveData<Boolean>{
        val isUpdated=MutableLiveData<Boolean> ()
        val call :Call<Boolean> =retrofitAPI.muteAllNotification(userId)
        call.enqueue(object :Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    isUpdated.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                isUpdated.value=false
            }

        })
        return isUpdated
    }
    fun unMuteAllNotification(userId: Long):LiveData<Boolean>{
        val isUpdated=MutableLiveData<Boolean> ()
        val call :Call<Boolean> =retrofitAPI.unMuteAllNotification(userId)
        call.enqueue(object :Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    isUpdated.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                isUpdated.value=false
            }

        })
        return isUpdated
    }
}