package com.example.gethired.github.githubEndPoint

import com.example.gethired.github.entities.Repository
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiService {
    @GET("users/{username}/repos")
    suspend fun getUserRepositories(@Path("username") username: String): List<Repository>
}