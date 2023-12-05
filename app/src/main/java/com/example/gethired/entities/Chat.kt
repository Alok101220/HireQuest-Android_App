package com.example.gethired.entities

class Chat(
    val id: Long,
    val senderId: Long,
    val receiverId: Long,
    val content: String,
    val timestamp: String,
    val seen: Boolean
)