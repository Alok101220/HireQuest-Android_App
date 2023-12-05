package com.example.gethired.Callback

import com.example.gethired.entities.Education

interface EducationCallback {
    fun onEducationResponse(education: Education)
    fun onEducationError()
}