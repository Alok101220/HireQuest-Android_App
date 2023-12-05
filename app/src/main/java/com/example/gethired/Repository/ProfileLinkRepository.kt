package com.example.gethired.Repository

import androidx.lifecycle.MutableLiveData
import com.example.gethired.Callback.ProfileCallback
import com.example.gethired.Token.TokenManager
import com.example.gethired.api.RetrofitClient
import com.example.gethired.api.retrofitInterface.RetrofitInterfaceResumeAddUpdate
import com.example.gethired.entities.Profile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileLinkRepository(tokenManager: TokenManager) {
    private val retrofitAPI: RetrofitInterfaceResumeAddUpdate = RetrofitClient(tokenManager).getApiService()

    fun addProfileLink(profile: Profile, candidateProfileId:Long, callback: ProfileCallback){
        val call: Call<Profile> = retrofitAPI.addProfileLink(profile,candidateProfileId)

        call.enqueue(object: Callback<Profile> {
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                if(response.isSuccessful&&response.body()!=null){
                    callback.profileLinkOnResponse(response.body()!!)
                }else{
                    callback.profileLinkOnError()
                }
            }
            override fun onFailure(call: Call<Profile>, t: Throwable) {
                callback.profileLinkOnError()
            }
        })
    }

    fun updateProfileLink(profile: Profile, profileLinkId:Long, callback: ProfileCallback){
        val call: Call<Profile> = retrofitAPI.updateProfileLink(profile,profileLinkId)
        call.enqueue(object: Callback<Profile> {
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                if(response.isSuccessful&&response.body()!=null){
                    callback.profileLinkOnResponse(response.body()!!)
                }else{
                    callback.profileLinkOnError()
                }
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                callback.profileLinkOnError()
            }

        })
    }

    fun deleteProfileLink(profileLinkId:Long){
        val call: Call<String> =retrofitAPI.deleteProfileLink(profileLinkId)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

            }

            override fun onFailure(call: Call<String>, t: Throwable) {

            }

        })
    }
    fun getAllProfileLink(candidateProfileId: Long): MutableLiveData<List<Profile>> {
        val profileLiveData = MutableLiveData<List<Profile>>()
        val call : Call<List<Profile>> = retrofitAPI.getAllProfileLink(candidateProfileId)
        call.enqueue(object : Callback<List<Profile>> {
            override fun onResponse(
                call: Call<List<Profile>>,
                response: Response<List<Profile>>
            ) {
                if(response.isSuccessful&&response.body()!=null){
                    profileLiveData.value=response.body()
                }else{
                    profileLiveData.value= emptyList()
                }
            }

            override fun onFailure(call: Call<List<Profile>>, t: Throwable) {

            }

        })
        return profileLiveData
    }

}