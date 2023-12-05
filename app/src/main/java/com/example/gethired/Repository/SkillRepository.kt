package com.example.gethired.Repository

import com.example.gethired.Callback.CommonCallback
import com.example.gethired.Token.TokenManager
import com.example.gethired.api.RetrofitClient
import com.example.gethired.api.retrofitInterface.RetrofitInterfaceResumeAddUpdate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SkillRepository(tokenManager: TokenManager) {

    private val retrofitAPI: RetrofitInterfaceResumeAddUpdate = RetrofitClient(tokenManager).getApiService()

        fun getAllSkills(userProfileId: Long,callback: CommonCallback){
            val call: Call<List<String>> = retrofitAPI.getAllSkill(userProfileId)
            call.enqueue(object : Callback<List<String>> {
                override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                    if (response.isSuccessful) {
                        val skill = response.body()
                        if (skill != null) {
                            callback.onAddResponse(skill)
                        }
                        else{
                            callback.onAddResponse(listOf())
                        }
                    } else {
                        callback.onAddError()
                    }
                }

                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    // Handle failure if needed
                    callback.onAddError()
                }
            })
        }


    fun addSkill(skill: List<String>, userProfileId:Long,callback: CommonCallback) {
        val call: Call<List<String>> = retrofitAPI.addSkill(skill, userProfileId)
        call.enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val skills = response.body()
                    if (skills != null) {
                        callback.onAddResponse(skills)
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