package com.example.gethired.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.gethired.Callback.ProfileCallback
import com.example.gethired.Repository.ProfileLinkRepository
import com.example.gethired.Token.TokenManager
import com.example.gethired.entities.Profile

class ProfileViewModel(tokenManager: TokenManager) :ViewModel() {
    private val profileLinkRepository=ProfileLinkRepository(tokenManager)

    fun addProfileLink(profile: Profile, candidateProfileId:Long, callback: ProfileCallback){
        profileLinkRepository.addProfileLink(profile,candidateProfileId,object: ProfileCallback {
            override fun profileLinkOnResponse(profile: Profile) {
                callback.profileLinkOnResponse(profile)
            }

            override fun profileLinkOnError() {
                callback.profileLinkOnError()
            }

        })
    }

    fun updateProfileLink(profile: Profile, profileLinkId:Long, callback: ProfileCallback){
        profileLinkRepository.updateProfileLink(profile,profileLinkId,object : ProfileCallback {
            override fun profileLinkOnResponse(profile: Profile) {
                callback.profileLinkOnResponse(profile)
            }

            override fun profileLinkOnError() {
                callback.profileLinkOnError()
            }
        })
    }

    fun deleteProfileLink(profileLinkId:Long){
        profileLinkRepository.deleteProfileLink(profileLinkId)
    }

    fun getAllProfileLink(candidateProfileId: Long): LiveData<List<Profile>> {
        return profileLinkRepository.getAllProfileLink(candidateProfileId)
    }

}