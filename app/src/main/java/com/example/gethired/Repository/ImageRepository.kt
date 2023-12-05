package com.example.gethired.Repository

import com.example.gethired.Callback.ImageCallback
import com.example.gethired.Token.TokenManager
import com.example.gethired.api.RetrofitClient
import com.example.gethired.api.retrofitInterface.RetrofitInterfaceResumeAddUpdate
import com.example.gethired.entities.Image
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageRepository(tokenManager: TokenManager) {

    private val retrofitAPI: RetrofitInterfaceResumeAddUpdate = RetrofitClient(tokenManager).getApiService()


    fun addProfileImage(userId:Long,file:MultipartBody.Part,callback:ImageCallback){

        val call : Call<Image> = retrofitAPI.uploadImage(userId,file)

        call.enqueue(object : Callback<Image> {
            override fun onResponse(call: Call<Image>, response: Response<Image>) {
                if(response.isSuccessful){
                    if(response.body()!=null){
                        callback.onImageResponse(response.body()!!)
                    }
                    else{
                        callback.onImageError()
                    }
                }
            }

            override fun onFailure(call: Call<Image>, t: Throwable) {
                callback.onImageError()
            }

        })
    }
}