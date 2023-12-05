package com.example.gethired.Repository

import androidx.lifecycle.MutableLiveData
import com.example.gethired.Callback.AppreciationCallback
import com.example.gethired.Token.TokenManager
import com.example.gethired.api.RetrofitClient
import com.example.gethired.api.retrofitInterface.RetrofitInterfaceResumeAddUpdate
import com.example.gethired.entities.Appreciation
import com.example.gethired.entities.Education
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppreciationRepository(tokenManager: TokenManager) {

    private val retrofitAPI: RetrofitInterfaceResumeAddUpdate = RetrofitClient(tokenManager).getApiService()


    fun addAppreciation(appreciation: Appreciation,userProfileId:Long, callback:AppreciationCallback){

        val call : Call<Appreciation> = retrofitAPI.addAppreciation(appreciation,userProfileId)

        call.enqueue(object : Callback<Appreciation> {
            override fun onResponse(call: Call<Appreciation>, response: Response<Appreciation>) {
                if(response.isSuccessful&&response.body()!=null){
                    callback.onAppreciationResponse(response.body()!!)
                }else{
                    callback.onAppreciationError()
                }
            }

            override fun onFailure(call: Call<Appreciation>, t: Throwable) {
                callback.onAppreciationError()
            }

        })

    }

    fun updateAppreciation(appreciation: Appreciation,appreciationId:Long, callback:AppreciationCallback){

        val call : Call<Appreciation> = retrofitAPI.updateAppreciation(appreciation,appreciationId)

        call.enqueue(object : Callback<Appreciation> {
            override fun onResponse(call: Call<Appreciation>, response: Response<Appreciation>) {
                if(response.isSuccessful&&response.body()!=null){
                    callback.onAppreciationResponse(response.body()!!)
                }else{
                    callback.onAppreciationError()
                }
            }

            override fun onFailure(call: Call<Appreciation>, t: Throwable) {
                callback.onAppreciationError()
            }

        })

    }

    fun getAllAppreciation(userProfileId: Long): MutableLiveData<List<Appreciation>> {
        val appreciationLiveData= MutableLiveData<List<Appreciation>>()

        val call:Call<List<Appreciation>> =retrofitAPI.getAllAppreciation(userProfileId)
        call.enqueue(object:Callback<List<Appreciation>>{
            override fun onResponse(
                call: Call<List<Appreciation>>,
                response: Response<List<Appreciation>>
            ) {
                if(response.isSuccessful&&response.body()!=null){
                    appreciationLiveData.value=response.body()
                }else{
                    appreciationLiveData.value= emptyList()
                }

            }

            override fun onFailure(call: Call<List<Appreciation>>, t: Throwable) {
            }

        })
        return appreciationLiveData
    }

    fun deleteAppreciation(appreciationId: Long) {

    }


}