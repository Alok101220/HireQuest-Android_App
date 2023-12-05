package com.example.gethired.Callback

import com.example.gethired.entities.Education

interface GetEducationCallback {
    fun onEducaitonListCallBack(education:List<Education>)
}