package com.example.gethired.ViewModel

import androidx.lifecycle.ViewModel
import com.example.gethired.Callback.CommonCallback
import com.example.gethired.Repository.HobbiesRepository
import com.example.gethired.Token.TokenManager

class HobbiesViewModel (tokenManager: TokenManager): ViewModel(){
    private val hobbiesRepository=HobbiesRepository(tokenManager)
    fun addHobbies(language: List<String>,userProfileId:Long,callback: CommonCallback){
        hobbiesRepository.addHobbies(language,userProfileId,object: CommonCallback {
            override fun onAddResponse(response: List<String>) {
                callback.onAddResponse(response)
            }

            override fun onAddError() {
                callback.onAddError()
            }

        })
    }


    fun getAllHobbies(userProfileId: Long,callback: CommonCallback){
        hobbiesRepository.getAllHobbies(userProfileId,object : CommonCallback {
            override fun onAddResponse(response: List<String>) {
                callback.onAddResponse(response)
            }

            override fun onAddError() {
                callback.onAddError()
            }

        })
    }
}
