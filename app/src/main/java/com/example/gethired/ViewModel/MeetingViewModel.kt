package com.example.gethired.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.gethired.Callback.MeetingCallback
import com.example.gethired.Repository.MeetingRepository
import com.example.gethired.Token.TokenManager
import com.example.gethired.entities.Meeting

class MeetingViewModel(tokenManager: TokenManager):ViewModel() {
    private val meetingRepository=MeetingRepository(tokenManager)

    fun createMeeting(meeting: Meeting, callback:MeetingCallback){
        meetingRepository.createMeeting(meeting, object :MeetingCallback{
            override fun onMeetingResponse(meeting: Meeting) {
                callback.onMeetingResponse(meeting)
            }

            override fun onMeetingError(message:String) {
                callback.onMeetingError(message)
            }

        })
    }

    fun updateMeeting(user:String, hr:String,meeting: Meeting, callback:MeetingCallback){
        meetingRepository.updateMeeting(user,hr,meeting, object :MeetingCallback{
            override fun onMeetingResponse(meeting: Meeting) {
                callback.onMeetingResponse(meeting)
            }

            override fun onMeetingError(message:String) {
                callback.onMeetingError(message)
            }

        })
    }
    fun getAllMeeting(user:String):LiveData<List<Meeting>>{
        return meetingRepository.getAllMeeting(user)
    }
    fun getAllPastMeeting(user:String):LiveData<List<Meeting>>{
        return meetingRepository.getAllPastMeeting(user)
    }

}