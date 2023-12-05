package com.example.gethired.entities

import java.io.Serializable

data class User(
    val id:Long,
    val address: Address,
    val birthdate: String,
    val createdAt: String,
    val createdBy: String,
    val currentOccupation: String,
    val email: String,
    val headline: String,
    val image: Image,
    val name: String,
    val phone: String,
    val status: Boolean,
    val updatedAt: String,
    val updatedBy: String,
    val username: String,
    var fcmToken:String
): Serializable