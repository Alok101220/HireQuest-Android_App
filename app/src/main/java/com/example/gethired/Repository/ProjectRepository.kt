package com.example.gethired.Repository

import androidx.lifecycle.MutableLiveData
import com.example.gethired.Callback.ProjectCallback
import com.example.gethired.Token.TokenManager
import com.example.gethired.api.RetrofitClient
import com.example.gethired.api.retrofitInterface.RetrofitInterfaceResumeAddUpdate
import com.example.gethired.entities.Project
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectRepository(tokenManager: TokenManager) {
    private val retrofitAPI: RetrofitInterfaceResumeAddUpdate = RetrofitClient(tokenManager).getApiService()

    fun addProject(candidateProfileId:Long, project: Project, callBack: ProjectCallback){
        val call: Call<Project> = retrofitAPI.addProject(project,candidateProfileId)
        call.enqueue(object: Callback<Project> {
            override fun onResponse(call: Call<Project>, response: Response<Project>) {
                if(response.isSuccessful&&response.body()!=null){
                    callBack.projectOnResponse(response.body()!!)
                }else{
                    callBack.projectOnError()
                }
            }
            override fun onFailure(call: Call<Project>, t: Throwable) {
                callBack.projectOnError()
            }

        })
    }

    fun updateProject(projectId:Long, project: Project, callBack: ProjectCallback){
        val call: Call<Project> = retrofitAPI.updateProject(project,projectId)
        call.enqueue(object: Callback<Project> {
            override fun onResponse(call: Call<Project>, response: Response<Project>) {
                if(response.isSuccessful&&response.body()!=null){
                    callBack.projectOnResponse(response.body()!!)
                }else{
                    callBack.projectOnError()
                }
            }
            override fun onFailure(call: Call<Project>, t: Throwable) {
                callBack.projectOnError()
            }

        })
    }

    fun deleteProject(projectId: Long){
        val call: Call<String> = retrofitAPI.deleteProject(projectId)
        call.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
            }

        })
    }

    fun getProject(projectId: Long,callBack: ProjectCallback){
        val call: Call<Project> = retrofitAPI.getProject(projectId)
        call.enqueue(object: Callback<Project> {
            override fun onResponse(call: Call<Project>, response: Response<Project>) {
                if(response.isSuccessful&&response.body()!=null){
                    callBack.projectOnResponse(response.body()!!)
                }else{
                    callBack.projectOnError()
                }
            }
            override fun onFailure(call: Call<Project>, t: Throwable) {
                callBack.projectOnError()
            }

        })
    }
    fun getAllProject(candidateId: Long): MutableLiveData<List<Project>> {
        val projectLiveData= MutableLiveData<List<Project>>()
        val call: Call<List<Project>> = retrofitAPI.getAllProject(candidateId)
        call.enqueue(object: Callback<List<Project>> {
            override fun onResponse(
                call: Call<List<Project>>,
                response: Response<List<Project>>
            ) {
                if(response.isSuccessful&&response.body()!=null){
                    projectLiveData.value=response.body()
                }else{
                    projectLiveData.value= emptyList()
                }
            }

            override fun onFailure(call: Call<List<Project>>, t: Throwable) {

            }

        })
        return projectLiveData

    }
}