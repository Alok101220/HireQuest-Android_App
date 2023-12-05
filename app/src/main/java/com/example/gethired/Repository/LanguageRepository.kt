package com.example.gethired.Repository

import androidx.lifecycle.MutableLiveData
import com.example.gethired.Callback.CommonCallback
import com.example.gethired.Token.TokenManager
import com.example.gethired.api.RetrofitClient
import com.example.gethired.api.retrofitInterface.RetrofitInterfaceResumeAddUpdate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LanguageRepository(tokenManager: TokenManager) {

    private val retrofitAPI: RetrofitInterfaceResumeAddUpdate = RetrofitClient(tokenManager).getApiService()

    fun getAllLanguages(userProfileId: Long,callback: CommonCallback){
        val call: Call<List<String>> = retrofitAPI.getAllLanguage(userProfileId)
        call.enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val language = response.body()
                    if (language != null) {
                        callback.onAddResponse(language)
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

    fun addLanguage(language: List<String>, userProfileId:Long,callback: CommonCallback) {
        val call: Call<List<String>> = retrofitAPI.addLanguage(language, userProfileId)
        call.enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val languages = response.body()
                    if (languages != null) {
                        callback.onAddResponse(languages)
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