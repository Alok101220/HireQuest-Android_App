package com.example.gethired.Callback

import com.example.gethired.Token.LoginResponse
import com.example.gethired.entities.OtpResponse

interface OtpCallback {
    fun onResponseOtp(otpResponse: OtpResponse)
    fun onErrorOtp()
}