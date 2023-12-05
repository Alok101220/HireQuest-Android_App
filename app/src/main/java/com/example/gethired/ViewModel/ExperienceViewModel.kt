package com.example.gethired.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.gethired.Callback.ExperienceCallback
import com.example.gethired.Repository.ExperienceRepository
import com.example.gethired.Token.TokenManager
import com.example.gethired.entities.Experience

class ExperienceViewModel(tokenManager: TokenManager) :ViewModel() {
    private val experienceRepository= ExperienceRepository(tokenManager)

    fun addExperience(experience: Experience, candidateProfileId:Long, callback: ExperienceCallback){
        experienceRepository.addExperience(candidateProfileId,experience,object : ExperienceCallback {
            override fun onExperienceResponse(experience: Experience) {
                callback.onExperienceResponse(experience)
            }

            override fun onExperienceError() {
                callback.onExperienceError()
            }

        })
    }

    fun updateExperience(experience: Experience, experienceId: Long, callback: ExperienceCallback){
        experienceRepository.updateExperience(experienceId, experience,object:
            ExperienceCallback {
            override fun onExperienceResponse(experience: Experience) {
                callback.onExperienceResponse(experience)
            }

            override fun onExperienceError() {
                callback.onExperienceError()
            }

        })
    }
    fun getExperience(experienceId:Long,callback: ExperienceCallback){
        experienceRepository.getExperience(experienceId,object:
            ExperienceCallback {
            override fun onExperienceResponse(experience: Experience) {
                callback.onExperienceResponse(experience)
            }

            override fun onExperienceError() {
                callback.onExperienceError()
            }

        })
    }
    fun getAllExperience(candidateId: Long): LiveData<List<Experience>> {
        return experienceRepository.getAllExperience(candidateId)
    }

    fun deleteExperience(experienceId: Long){
        experienceRepository.deleteExperience(experienceId)
    }
}