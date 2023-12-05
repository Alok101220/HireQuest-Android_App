package com.example.gethired.Repository

import androidx.lifecycle.MutableLiveData
import com.example.gethired.Callback.CertificateCallback
import com.example.gethired.Token.TokenManager
import com.example.gethired.api.RetrofitClient
import com.example.gethired.api.retrofitInterface.RetrofitInterfaceResumeAddUpdate
import com.example.gethired.entities.Certificate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CertificateRepository(tokenManager: TokenManager) {

    private val retrofitAPI: RetrofitInterfaceResumeAddUpdate = RetrofitClient(tokenManager).getApiService()

    fun addCertificate(candidateId:Long, certificate: Certificate, callBack: CertificateCallback){
        val call: Call<Certificate> = retrofitAPI.addCertificate(certificate,candidateId)
        call.enqueue(object: Callback<Certificate> {
            override fun onResponse(call: Call<Certificate>, response: Response<Certificate>) {
                if(response.isSuccessful&&response.body()!=null){
                    callBack.certificateOnResponse(response.body()!!)
                }else{
                    callBack.certificateOnError()
                }
            }
            override fun onFailure(call: Call<Certificate>, t: Throwable) {
                callBack.certificateOnError()
            }

        })
    }

    fun updateCertificate(certificateId:Long,certificate:Certificate,callBack: CertificateCallback){
        val call: Call<Certificate> = retrofitAPI.updateCertificate(certificateId,certificate)
        call.enqueue(object: Callback<Certificate> {
            override fun onResponse(call: Call<Certificate>, response: Response<Certificate>) {
                if(response.isSuccessful&&response.body()!=null){
                    callBack.certificateOnResponse(response.body()!!)
                }else{
                    callBack.certificateOnError()
                }
            }
            override fun onFailure(call: Call<Certificate>, t: Throwable) {
                callBack.certificateOnError()
            }

        })
    }

    fun deleteCertificate(certificateId: Long){
        val call:Call<String> = retrofitAPI.deleteCertificate(certificateId)
        call.enqueue(object:Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
            }

        })
    }

    fun getCertificate(certificateId: Long,callBack: CertificateCallback){
        val call: Call<Certificate> = retrofitAPI.getCertificate(certificateId)
        call.enqueue(object: Callback<Certificate> {
            override fun onResponse(call: Call<Certificate>, response: Response<Certificate>) {
                if(response.isSuccessful&&response.body()!=null){
                    callBack.certificateOnResponse(response.body()!!)
                }else{
                    callBack.certificateOnError()
                }
            }
            override fun onFailure(call: Call<Certificate>, t: Throwable) {
                callBack.certificateOnError()
            }

        })
    }
    fun getAllCertificate(candidateId: Long):MutableLiveData<List<Certificate>>{
        val certificateLiveData=MutableLiveData<List<Certificate>>()
        val call:Call<List<Certificate>> = retrofitAPI.getAllCertificate(candidateId)
        call.enqueue(object:Callback<List<Certificate>>{
            override fun onResponse(
                call: Call<List<Certificate>>,
                response: Response<List<Certificate>>
            ) {
                if(response.isSuccessful&&response.body()!=null){
                    certificateLiveData.value=response.body()
                }else{
                    certificateLiveData.value= emptyList()
                }
            }

            override fun onFailure(call: Call<List<Certificate>>, t: Throwable) {

            }

        })
        return certificateLiveData

    }
}