package com.example.gethired.ViewModel

import androidx.lifecycle.ViewModel
import com.example.gethired.Callback.CommonCallback
import com.example.gethired.Repository.SkillRepository
import com.example.gethired.Token.TokenManager

class SkillViewModel(tokenManager: TokenManager) : ViewModel() {
    private val skillRepository = SkillRepository(tokenManager)

    // Function to fetch all skills for a given userProfileId
    fun getSkills(userProfileId: Long,callback: CommonCallback){
        return skillRepository.getAllSkills(userProfileId,object :CommonCallback{
            override fun onAddResponse(response: List<String>) {
                callback.onAddResponse(response)
            }

            override fun onAddError() {
                callback.onAddError()
            }

        })
    }


    // Function to add a new skill
    fun addSkill(skill: List<String>,userProfileId: Long, callback: CommonCallback) {
        skillRepository.addSkill(skill,userProfileId, object : CommonCallback {
            override fun onAddResponse(response: List<String>) {
                callback.onAddResponse(response)
            }

            override fun onAddError() {
                callback.onAddError()
            }

        })
    }

}