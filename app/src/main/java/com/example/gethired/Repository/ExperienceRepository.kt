package com.example.gethired.Repository

import androidx.lifecycle.MutableLiveData
import com.example.gethired.Callback.ExperienceCallback
import com.example.gethired.Token.TokenManager
import com.example.gethired.api.RetrofitClient
import com.example.gethired.api.retrofitInterface.RetrofitInterfaceResumeAddUpdate
import com.example.gethired.entities.Experience
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExperienceRepository(tokenManager: TokenManager) {
    private val retrofitAPI: RetrofitInterfaceResumeAddUpdate = RetrofitClient(tokenManager).getApiService()

    fun addExperience(candidateId:Long, experience: Experience, callBack: ExperienceCallback){
        val call: Call<Experience> = retrofitAPI.addExperience(experience,candidateId)
        call.enqueue(object: Callback<Experience> {
            override fun onResponse(call: Call<Experience>, response: Response<Experience>) {
                if(response.isSuccessful&&response.body()!=null){
                    callBack.onExperienceResponse(response.body()!!)
                }else{
                    callBack.onExperienceError()
                }
            }
            override fun onFailure(call: Call<Experience>, t: Throwable) {
                callBack.onExperienceError()
            }

        })
    }

    fun updateExperience(experienceId:Long,experience: Experience, callBack: ExperienceCallback){
        val call: Call<Experience> = retrofitAPI.updateExperience(experienceId,experience)
        call.enqueue(object: Callback<Experience> {
            override fun onResponse(call: Call<Experience>, response: Response<Experience>) {
                if(response.isSuccessful&&response.body()!=null){
                    callBack.onExperienceResponse(response.body()!!)
                }else{
                    callBack.onExperienceError()
                }
            }
            override fun onFailure(call: Call<Experience>, t: Throwable) {
                callBack.onExperienceError()
            }

        })
    }

    fun deleteExperience(experienceId: Long){
        val call: Call<String> = retrofitAPI.deleteExperience(experienceId)
        call.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
            }

        })
    }

    fun getExperience(experienceId: Long,callBack: ExperienceCallback){
        val call: Call<Experience> = retrofitAPI.getExperience(experienceId)
        call.enqueue(object: Callback<Experience> {
            override fun onResponse(call: Call<Experience>, response: Response<Experience>) {
                if(response.isSuccessful&&response.body()!=null){
                    callBack.onExperienceResponse(response.body()!!)
                }else{
                    callBack.onExperienceError()
                }
            }
            override fun onFailure(call: Call<Experience>, t: Throwable) {
                callBack.onExperienceError()
            }

        })
    }
    fun getAllExperience(candidateId: Long): MutableLiveData<List<Experience>> {
        val experienceLiveData= MutableLiveData<List<Experience>>()
        val call: Call<List<Experience>> = retrofitAPI.getAllExperience(candidateId)
        call.enqueue(object: Callback<List<Experience>> {
            override fun onResponse(
                call: Call<List<Experience>>,
                response: Response<List<Experience>>
            ) {
                if(response.isSuccessful&&response.body()!=null){
                    experienceLiveData.value=response.body()
                }else{
                    experienceLiveData.value= emptyList()
                }
            }

            override fun onFailure(call: Call<List<Experience>>, t: Throwable) {

            }

        })
        return experienceLiveData

    }

}