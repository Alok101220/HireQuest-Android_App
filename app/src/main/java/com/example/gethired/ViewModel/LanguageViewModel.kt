package com.example.gethired.ViewModel

import androidx.lifecycle.ViewModel
import com.example.gethired.Callback.CommonCallback
import com.example.gethired.Repository.LanguageRepository
import com.example.gethired.Token.TokenManager

class LanguageViewModel(tokenManager: TokenManager) : ViewModel() {
    private var languageRepository = LanguageRepository(tokenManager)

    fun addLanguage(language: List<String>,userProfileId:Long,callback:CommonCallback){
        languageRepository.addLanguage(language,userProfileId,object:CommonCallback{
            override fun onAddResponse(response: List<String>) {
                callback.onAddResponse(response)
            }

            override fun onAddError() {
                callback.onAddError()
            }

        })
    }


    fun getAllLanguage(userProfileId: Long,callback: CommonCallback){
        languageRepository.getAllLanguages(userProfileId,object :CommonCallback{
            override fun onAddResponse(response: List<String>) {
                callback.onAddResponse(response)
            }

            override fun onAddError() {
                callback.onAddError()
            }

        })
    }

}