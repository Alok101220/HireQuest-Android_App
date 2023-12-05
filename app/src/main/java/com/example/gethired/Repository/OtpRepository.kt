package com.example.gethired.Repository

import com.example.gethired.Callback.OtpCallback
import com.example.gethired.Callback.ResponseCallback
import com.example.gethired.api.RetrofitClient
import com.example.gethired.api.retrofitInterface.RetrofitInterfaceResumeAddUpdate
import com.example.gethired.entities.OtpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpRepository() {
    private val retrofitAPI: RetrofitInterfaceResumeAddUpdate = RetrofitClient().getApiService()

    fun sendOtp(email:String,callback: OtpCallback){
        val call: Call<OtpResponse> = retrofitAPI.sendOtp(email)
        call.enqueue(object :Callback<OtpResponse>{
            override fun onResponse(call: Call<OtpResponse>, response: Response<OtpResponse>) {
                if(response.isSuccessful&&response.body()!=null){
                    callback.onResponseOtp(response.body()!!)
                }
            }

            override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                callback.onErrorOtp()
            }

        })
    }

    fun verify(otpCode:String,email:String, callback:ResponseCallback){
        val call : Call<com.example.gethired.entities.Response>  =retrofitAPI.verifyOtp(otpCode,email)
        call.enqueue(object :Callback<com.example.gethired.entities.Response>{
            override fun onResponse(
                call: Call<com.example.gethired.entities.Response>,
                response: Response<com.example.gethired.entities.Response>
            ) {
                if (response.isSuccessful&&response.body()!=null){
                    callback.onResponseCallback(response.body()!!)
                }
            }

            override fun onFailure(
                call: Call<com.example.gethired.entities.Response>,
                t: Throwable
            ) {
                callback.onErrorResponseCallback()
            }

        })
    }

}