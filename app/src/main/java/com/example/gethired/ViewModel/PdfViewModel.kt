package com.example.gethired.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.gethired.Callback.PdfCallback
import com.example.gethired.Repository.PdfRepository
import com.example.gethired.Token.TokenManager
import com.example.gethired.entities.Resume
import com.example.gethired.entities.Pdf
import okhttp3.MultipartBody
import okhttp3.ResponseBody

class PdfViewModel(tokenManager: TokenManager): ViewModel() {

    private val pdfRepository=PdfRepository(tokenManager)

    fun addPdf(userProfileId:Long, file: MultipartBody.Part,callback:PdfCallback){
        pdfRepository.addPdf(userProfileId,file,object :PdfCallback{
            override fun onPdfCallback(pdf: Pdf) {
                callback.onPdfCallback(pdf)
            }

            override fun onPdfError(error: String) {
                callback.onPdfError("Failed to add pdf")
            }

        })
    }

    fun deletePdf(pdfId:Long){
        pdfRepository.deletePdf(pdfId)
    }

    fun getAllPdf(userProfileId: Long):LiveData<List<Pdf>>{
        return pdfRepository.getAllPdf(userProfileId)
    }

    fun downloadPdf(pdfId: Long):LiveData<ResponseBody>{
        return pdfRepository.downloadPdf(pdfId)
    }
}