package com.example.gethired.Repository

import com.example.gethired.Token.TokenManager
import com.example.gethired.api.RetrofitClient
import com.example.gethired.api.retrofitInterface.RetrofitInterfaceResumeAddUpdate
import com.example.gethired.entities.RecentSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecentSearchRepository(tokenManager: TokenManager) {

    private val retrofitAPI: RetrofitInterfaceResumeAddUpdate = RetrofitClient(tokenManager).getApiService()

    fun addRecentSearch(recentSearchDto: RecentSearch, onSuccess: (RecentSearch) -> Unit, onFailure: (Throwable) -> Unit) {
        val call = retrofitAPI.addRecentSearch(recentSearchDto)
        call.enqueue(object : Callback<RecentSearch> {
            override fun onResponse(call: Call<RecentSearch>, response: Response<RecentSearch>) {
                if (response.isSuccessful) {
                    val recentSearch = response.body()
                    recentSearch?.let { onSuccess(it) }
                } else {
                    onFailure(Throwable("Failed to add recent search"))
                }
            }

            override fun onFailure(call: Call<RecentSearch>, t: Throwable) {
                onFailure(t)
            }
        })
    }
    fun getAllRecentSearches(userId: Long, onSuccess: (List<RecentSearch>) -> Unit, onFailure: (Throwable) -> Unit) {
        val call = retrofitAPI.getAllRecentSearch(userId)
        call.enqueue(object : Callback<List<RecentSearch>> {
            override fun onResponse(call: Call<List<RecentSearch>>, response: Response<List<RecentSearch>>) {
                if (response.isSuccessful) {
                    val recentSearches = response.body()
                    recentSearches?.let { onSuccess(it) }
                } else {
                    onFailure(Throwable("Failed to get recent searches"))
                }
            }

            override fun onFailure(call: Call<List<RecentSearch>>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    fun deleteRecentSearch(recentSearchId: Long, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {
        val call = retrofitAPI.deleteRecentSearch(recentSearchId)
        call.enqueue(object : Callback<String> {  // Changed from Callback<Void> to Callback<String>
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(Throwable("Failed to delete recent search"))
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                onFailure(t)
            }
        })
    }


    fun deleteAllRecentSearches(userId: Long, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {
        val call = retrofitAPI.deleteAllRecentSearch(userId)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(Throwable("Failed to delete all recent searches"))
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}