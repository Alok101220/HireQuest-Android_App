package com.example.gethired.entities

data class UserProfile(
    val about: String,
    val createdAt: String,
    val createdBy: String,
    val id: Int,
    val languages: List<String>,
    val hobbies:List<String>,
    val skills: List<String>,
    val updatedAt: String,
    val updatedBy: String,
    val user:User
)