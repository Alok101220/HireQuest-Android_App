package com.example.gethired.Repository

import androidx.lifecycle.MutableLiveData
import com.example.gethired.Callback.EducationCallback
import com.example.gethired.Token.TokenManager
import com.example.gethired.api.RetrofitClient
import com.example.gethired.api.retrofitInterface.RetrofitInterfaceResumeAddUpdate
import com.example.gethired.entities.Education
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EducationRepository(tokenManager: TokenManager) {
    private val retrofitAPI: RetrofitInterfaceResumeAddUpdate = RetrofitClient(tokenManager).getApiService()

    fun addEducation(candidateId:Long,education: Education, callBack: EducationCallback){
        val call: Call<Education> = retrofitAPI.addEducation(education,candidateId)
        call.enqueue(object:Callback<Education>{
            override fun onResponse(call: Call<Education>, response: Response<Education>) {
                if(response.isSuccessful&&response.body()!=null){
                    callBack.onEducationResponse(response.body()!!)
                }else{
                    callBack.onEducationError()
                }
            }

            override fun onFailure(call: Call<Education>, t: Throwable) {
                callBack.onEducationError()
            }

        })
    }
    fun getAllEducation(candidateProfileId:Long): MutableLiveData<List<Education>>{
        val educationLiveData= MutableLiveData<List<Education>>()

        val call:Call<List<Education>> =retrofitAPI.getAllEducation(candidateProfileId)
        call.enqueue(object:Callback<List<Education>>{
            override fun onResponse(
                call: Call<List<Education>>,
                response: Response<List<Education>>
            ) {
                if(response.isSuccessful&&response.body()!=null){
                    educationLiveData.value=response.body()
                }else{
                    educationLiveData.value= emptyList()
                }

            }

            override fun onFailure(call: Call<List<Education>>, t: Throwable) {
            }

        })
        return educationLiveData
    }

    fun updateEducation(education: Education,educationId:Long,callBack: EducationCallback){
        val call:Call<Education> =retrofitAPI.updateEducation(education,educationId)
        call.enqueue(object:Callback<Education>{
            override fun onResponse(call: Call<Education>, response: Response<Education>) {
                if(response.isSuccessful&&response.body()!=null){
                    callBack.onEducationResponse(education)
                }else{
                    callBack.onEducationError()
                }
            }

            override fun onFailure(call: Call<Education>, t: Throwable) {
                callBack.onEducationError()
            }

        })
    }

    fun deleteEducation(educationId: Long) {
        val call:Call<String> =retrofitAPI.deleteEducation(educationId)
        call.enqueue(object:Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {

            }

            override fun onFailure(call: Call<String>, t: Throwable) {

            }

        })
    }


}