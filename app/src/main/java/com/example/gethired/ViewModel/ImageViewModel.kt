package com.example.gethired.ViewModel

import androidx.lifecycle.ViewModel
import com.example.gethired.Callback.ImageCallback
import com.example.gethired.Repository.ImageRepository
import com.example.gethired.Token.TokenManager
import com.example.gethired.entities.Image
import okhttp3.Callback
import okhttp3.MultipartBody

class ImageViewModel(tokenManager: TokenManager): ViewModel() {

    private val imageRepository=ImageRepository(tokenManager)

    fun addProfileImage(userId:Long,file:MultipartBody.Part,callback: ImageCallback){
        imageRepository.addProfileImage(userId,file,object :ImageCallback{
            override fun onImageResponse(image: Image) {
                callback.onImageResponse(image)
            }

            override fun onImageError() {
                callback.onImageError()
            }

        })
    }
}