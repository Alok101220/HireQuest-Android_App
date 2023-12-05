package com.example.gethired.Callback

import com.example.gethired.entities.Project

interface ProjectCallback {
    fun projectOnResponse(project: Project)
    fun projectOnError()
}