package com.example.gethired.Callback

import com.example.gethired.entities.Profile

interface ProfileCallback {
    fun profileLinkOnResponse(profile: Profile)
    fun profileLinkOnError()
}