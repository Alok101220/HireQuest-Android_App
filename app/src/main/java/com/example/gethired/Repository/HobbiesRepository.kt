package com.example.gethired.Repository

import com.example.gethired.Callback.CommonCallback
import com.example.gethired.Token.TokenManager
import com.example.gethired.api.RetrofitClient
import com.example.gethired.api.retrofitInterface.RetrofitInterfaceResumeAddUpdate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HobbiesRepository(tokenManager: TokenManager) {

    private val retrofitAPI: RetrofitInterfaceResumeAddUpdate = RetrofitClient(tokenManager).getApiService()

    fun getAllHobbies(userProfileId: Long,callback: CommonCallback){
        val call: Call<List<String>> = retrofitAPI.getAllHobbies(userProfileId)
        call.enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val hobbies = response.body()
                    if (hobbies != null) {
                        callback.onAddResponse(hobbies)
                    }
                    else{
                        callback.onAddResponse(listOf())
                    }
                } else {
                    callback.onAddError()
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                callback.onAddError()
            }
        })
    }

    fun addHobbies(language: List<String>, userProfileId:Long,callback: CommonCallback) {
        val call: Call<List<String>> = retrofitAPI.addHobbies(language, userProfileId)
        call.enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val hobbies = response.body()
                    if (hobbies != null) {
                        callback.onAddResponse(hobbies)
                    }
                    else{
                        callback.onAddResponse(listOf())
                    }
                } else {
                    callback.onAddError()
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                callback.onAddError()
            }
        })
    }
}