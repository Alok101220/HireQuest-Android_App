package com.example.gethired.api

import com.example.gethired.Token.AuthInterceptor
import com.example.gethired.Token.TokenManager
import com.example.gethired.api.retrofitInterface.RetrofitInterfaceResumeAddUpdate
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "http://192.168.1.5:8080/api/hireQuest/"
//private const val BASE_URL = "http://192.168.14.254:8080/"

class RetrofitClient(private val tokenManager: TokenManager? = null) {

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().apply {
        tokenManager?.let { addInterceptor(AuthInterceptor(it)) }
    }.build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getApiService(): RetrofitInterfaceResumeAddUpdate {
        return retrofit.create(RetrofitInterfaceResumeAddUpdate::class.java)
    }
}
