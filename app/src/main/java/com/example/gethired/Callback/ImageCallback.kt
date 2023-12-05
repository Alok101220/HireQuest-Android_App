package com.example.gethired.Callback

import com.example.gethired.entities.Image

interface ImageCallback {

    fun onImageResponse(image:Image)
    fun onImageError()
}