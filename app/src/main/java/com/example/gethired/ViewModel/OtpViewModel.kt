package com.example.gethired.ViewModel

import androidx.lifecycle.ViewModel
import com.example.gethired.Callback.OtpCallback
import com.example.gethired.Callback.ResponseCallback
import com.example.gethired.Repository.OtpRepository
import com.example.gethired.entities.OtpResponse
import com.example.gethired.entities.Response

class OtpViewModel() : ViewModel() {
    private val otpRepository= OtpRepository()

    fun sendOtp(email:String,callback: OtpCallback){
        otpRepository.sendOtp(email,object :OtpCallback{
            override fun onResponseOtp(otpResponse: OtpResponse) {
                callback.onResponseOtp(otpResponse)
            }
            override fun onErrorOtp() {
                callback.onErrorOtp()
            }
        })
    }
    fun verifyOtp(otpCode:String,email: String,callback:ResponseCallback){
        otpRepository.verify(otpCode,email,object :ResponseCallback{
            override fun onResponseCallback(response: Response) {
                callback.onResponseCallback(response)
            }

            override fun onErrorResponseCallback() {
                callback.onErrorResponseCallback()
            }

        })
    }

}