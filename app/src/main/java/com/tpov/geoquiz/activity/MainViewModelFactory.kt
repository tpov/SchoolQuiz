package com.tpov.geoquiz.activity

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(
    private val typeQuestion: Boolean,
    private val percentAnswer: Int,
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FrontViewModel::class.java)) {
            return FrontViewModel(typeQuestion, percentAnswer, application) as T
        }
        throw RuntimeException("Unknown ViewModel class $modelClass")
    }
}