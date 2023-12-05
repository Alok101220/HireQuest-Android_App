package com.example.gethired.Repository

import android.os.Environment
import androidx.lifecycle.MutableLiveData
import com.example.gethired.Callback.PdfCallback
import com.example.gethired.Token.TokenManager
import com.example.gethired.api.RetrofitClient
import com.example.gethired.api.retrofitInterface.RetrofitInterfaceResumeAddUpdate
import com.example.gethired.entities.Pdf
import com.example.gethired.entities.Resume
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.StandardCharsets


class PdfRepository(tokenManager: TokenManager) {

    private val retrofitAPI: RetrofitInterfaceResumeAddUpdate =
        RetrofitClient(tokenManager).getApiService()

    fun addPdf(userProfileId: Long, file: MultipartBody.Part, callback: PdfCallback) {
        val call: Call<Pdf> = retrofitAPI.addPdf(userProfileId, file)
        call.enqueue(object : Callback<Pdf> {
            override fun onResponse(call: Call<Pdf>, response: Response<Pdf>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        callback.onPdfCallback(response.body()!!)
                    } else {
                        callback.onPdfError("Failed to add pdf")
                    }
                }
            }

            override fun onFailure(call: Call<Pdf>, t: Throwable) {
                callback.onPdfError("Network error : ${t.message}")
            }

        })
    }

    fun deletePdf(pdfId: Long) {
        val call = retrofitAPI.deletePdf(pdfId)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {

                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {

            }

        })
    }

    fun getAllPdf(userProfileId: Long): MutableLiveData<List<Pdf>> {
        val listOfPdfs = MutableLiveData<List<Pdf>>()

        val call: Call<List<Pdf>> = retrofitAPI.getAllPdf(userProfileId)
        call.enqueue(object : Callback<List<Pdf>> {
            override fun onResponse(call: Call<List<Pdf>>, response: Response<List<Pdf>>) {
                if (response.isSuccessful) {

                    if(response.body()!=null){
                        listOfPdfs.postValue(response.body())
                    }
                }
            }

            override fun onFailure(call: Call<List<Pdf>>, t: Throwable) {
                // Handle failure here if needed
                // e.g., log the error or show a message to the userLo
                Log.d("pdf-error", t.message.toString())
            }

        })

        return listOfPdfs
    }

    fun downloadPdf(pdfId: Long):LiveData<ResponseBody> {
        val downloadFile=MutableLiveData<ResponseBody>()
        val call = retrofitAPI.downloadPdf(pdfId)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {

                        downloadFile.postValue(response.body())

                    }
                } else {
                    // Handle unsuccessful download
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle failure
            }
        })
        return downloadFile

    }




}
