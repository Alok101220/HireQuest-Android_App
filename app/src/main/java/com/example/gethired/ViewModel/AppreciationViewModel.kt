package com.example.gethired.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.gethired.Callback.AppreciationCallback
import com.example.gethired.Repository.AppreciationRepository
import com.example.gethired.Token.TokenManager
import com.example.gethired.entities.Appreciation
import com.example.gethired.entities.Education

class AppreciationViewModel (tokenManager: TokenManager):ViewModel(){
    private val appreciationRepository=AppreciationRepository(tokenManager)


    fun addAppreciation(appreciation: Appreciation,userProfileId:Long, callback:AppreciationCallback){
        appreciationRepository.addAppreciation(appreciation,userProfileId,object :AppreciationCallback{
            override fun onAppreciationResponse(appreciation: Appreciation) {
                callback.onAppreciationResponse(appreciation)
            }

            override fun onAppreciationError() {
                callback.onAppreciationError()
            }

        })
    }

    fun updateAppreciation(appreciation: Appreciation,appreciationId:Long, callback: AppreciationCallback){
        appreciationRepository.updateAppreciation(appreciation,appreciationId,object :AppreciationCallback{
            override fun onAppreciationResponse(appreciation: Appreciation) {
                callback.onAppreciationResponse(appreciation)
            }

            override fun onAppreciationError() {
                callback.onAppreciationError()
            }

        })
    }

    fun getAllAppreciationId(userProfileId: Long): LiveData<List<Appreciation>> {
        return appreciationRepository.getAllAppreciation(userProfileId)
    }

    fun deleteAppreciation(appreciationId: Long){
        appreciationRepository.deleteAppreciation(appreciationId)
    }

}
