package com.example.gethired.github

import com.example.gethired.github.githubEndPoint.GitHubApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubRetrofitClient {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getGithubApiService():GitHubApiService{
        return retrofit.create(GitHubApiService::class.java)
    }
}