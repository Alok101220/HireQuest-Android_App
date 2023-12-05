package com.example.gethired.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.gethired.Callback.CertificateCallback
import com.example.gethired.Repository.CertificateRepository
import com.example.gethired.Token.TokenManager
import com.example.gethired.entities.Certificate

class CertificateViewModel(tokenManager: TokenManager) :ViewModel() {
    private val certificateRepository=CertificateRepository(tokenManager)

    fun addCertificate(certificate: Certificate,candidateId:Long,callback: CertificateCallback){
        certificateRepository.addCertificate(candidateId,certificate,object :CertificateCallback{
            override fun certificateOnResponse(certificate: Certificate) {
                callback.certificateOnResponse(certificate)
            }

            override fun certificateOnError() {
                callback.certificateOnError()
            }

        })
    }

    fun updateCertificate(certificate: Certificate,certificateId:Long,callback: CertificateCallback){
        certificateRepository.updateCertificate(certificateId,certificate,object:CertificateCallback{
            override fun certificateOnResponse(certificate: Certificate) {
                callback.certificateOnResponse(certificate)
            }

            override fun certificateOnError() {
                callback.certificateOnError()
            }

        })
    }
    fun getCertificate(certificateId:Long,callback: CertificateCallback){
        certificateRepository.getCertificate(certificateId,object:CertificateCallback{
            override fun certificateOnResponse(certificate: Certificate) {
                callback.certificateOnResponse(certificate)
            }

            override fun certificateOnError() {
                callback.certificateOnError()
            }

        })
    }
    fun getAllCertificate(candidateId: Long):LiveData<List<Certificate>>{
        return certificateRepository.getAllCertificate(candidateId)
    }

    fun deleteCertificate(certificateId: Long){
        certificateRepository.deleteCertificate(certificateId)
    }
}