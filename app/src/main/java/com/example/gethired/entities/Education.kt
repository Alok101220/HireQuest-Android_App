package com.example.gethired.entities

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gethired.api.RetrofitClient
import com.example.gethired.api.retrofitInterface.RetrofitInterfaceResumeAddUpdate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class Education(
    var id:Int,
    val end: String,
    val instituteName: String,
    val levelOfEducation: String,
    val start: String,
    val fieldOfStudy:String
)
