package com.example.gethired.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.gethired.Token.TokenManager
import com.example.gethired.ViewModel.ExperienceViewModel

class ExperienceViewModelFactory(private val tokenManager: TokenManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(ExperienceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExperienceViewModel(tokenManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
