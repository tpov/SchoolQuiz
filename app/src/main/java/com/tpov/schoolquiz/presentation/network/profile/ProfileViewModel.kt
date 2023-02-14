package com.tpov.schoolquiz.presentation.network.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.tpov.schoolquiz.domain.GetInfoQuestionListUseCase
import com.tpov.schoolquiz.domain.GetQuizListUseCase
import com.tpov.schoolquiz.domain.GetQuestionListUseCase
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@InternalCoroutinesApi
class ProfileViewModel @Inject constructor(
    private val getInfoQuestionListUseCase: GetInfoQuestionListUseCase,
    private val getQuizListUseCase: GetQuizListUseCase,
    private val getQuestionListUseCase: GetQuestionListUseCase
) : ViewModel() {
    init {
        Log.d("sfsef", "getQuestionListUseCase$getQuestionListUseCase")
        Log.d("sfsef", "getInfoQuestionListUseCase$getInfoQuestionListUseCase")
        Log.d("sfsef", "getQuizListUseCase$getQuizListUseCase")
    }
}