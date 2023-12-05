package com.example.gethired.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.gethired.Callback.ProjectCallback
import com.example.gethired.Repository.ProjectRepository
import com.example.gethired.Token.TokenManager
import com.example.gethired.entities.Project

class ProjectViewModel(tokenManager: TokenManager) : ViewModel() {
    private val projectRepository= ProjectRepository(tokenManager)

    fun addProject(project: Project, candidateProfileId:Long, callback: ProjectCallback){
        projectRepository.addProject(candidateProfileId,project,object:
            ProjectCallback {
            override fun projectOnResponse(project: Project) {
                callback.projectOnResponse(project)
            }

            override fun projectOnError() {
                callback.projectOnError()
            }
        })
    }

    fun updateProject(project: Project, projectId: Long, callback: ProjectCallback){
        projectRepository.updateProject(projectId, project,object:
            ProjectCallback {
            override fun projectOnResponse(project: Project) {
                callback.projectOnResponse(project)
            }

            override fun projectOnError() {
                callback.projectOnError()
            }

        })
    }
    fun getProject(projectId:Long,callback: ProjectCallback){
        projectRepository.getProject(projectId,object:
            ProjectCallback {
            override fun projectOnResponse(project: Project) {
                callback.projectOnResponse(project)
            }

            override fun projectOnError() {
                callback.projectOnError()
            }


        })
    }
    fun getAllProject(candidateId: Long): LiveData<List<Project>> {
        return projectRepository.getAllProject(candidateId)
    }

    fun deleteProject(projectId: Long){
        projectRepository.deleteProject(projectId)
    }
}
