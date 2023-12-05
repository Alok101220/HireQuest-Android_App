package com.example.gethired.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.gethired.Callback.NotificationCallback
import com.example.gethired.Repository.NotificationRepository
import com.example.gethired.Token.TokenManager
import com.example.gethired.entities.Notification
import com.example.gethired.entities.NotificationPreference
import com.example.gethired.entities.NotificationRequest

class NotificationViewModel(tokenManager: TokenManager) : ViewModel() {

    val notificationRepository=NotificationRepository(tokenManager)

    fun sendNotification(request: NotificationRequest){
        notificationRepository.sendNotification(request)
    }

    fun updateNotification(notificationId:Long,callback:NotificationCallback){
        notificationRepository.updateNotification(notificationId, object :NotificationCallback{
            override fun onNotificationResponse(notification: Notification) {
                callback.onNotificationResponse(notification)
            }

            override fun onNotificationError() {
                callback.onNotificationError()
            }

        })
    }

    fun deleteNotification(notificationId: Long){
        notificationRepository.deleteNotification(notificationId)
    }

    fun getAllNotification(userId:Long):LiveData<List<Notification>>{
        return notificationRepository.getAllNotification(userId)
    }


    fun muteAllNotification(userId: Long):LiveData<Boolean>{
        return notificationRepository.muteAllNotification(userId)
    }
    fun unMuteAllNotification(userId: Long):LiveData<Boolean>{
        return notificationRepository.unMuteAllNotification(userId)
    }

    fun updateNotificationPreference(notificationType:String, userId: Long):LiveData<Boolean>{
        return notificationRepository.updateNotificationPreference(notificationType,userId)
    }

    fun getNotificationPreference(userId: Long):LiveData<List<NotificationPreference>>{
        return notificationRepository.getAllNotificationPreference(userId)
    }

}