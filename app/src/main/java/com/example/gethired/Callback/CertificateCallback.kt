package com.example.gethired.Callback

import com.example.gethired.entities.Certificate


interface CertificateCallback {
    fun certificateOnResponse(certificate: Certificate)
    fun certificateOnError()
}