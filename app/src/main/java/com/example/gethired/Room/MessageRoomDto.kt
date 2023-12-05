package com.example.gethired.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "message_table")
data class MessageRoomDto(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val senderId: Long,
    val receiverId: Long,
    val content: String?,
    val timeStamp:LocalDateTime?,
    val seen:Boolean
)
