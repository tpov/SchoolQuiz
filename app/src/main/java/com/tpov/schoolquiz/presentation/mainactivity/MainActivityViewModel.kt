package com.tpov.schoolquiz.presentation.mainactivity

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.domain.InsertQuestionUseCase
import com.tpov.schoolquiz.domain.InsertQuizUseCase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
class MainActivityViewModel @Inject constructor(
    private val insertQuizUseCase: InsertQuizUseCase,
    private val insertQuestionUseCase: InsertQuestionUseCase
) : ViewModel() {



    fun insertQuiz(quiz: Quiz) = viewModelScope.launch { insertQuizUseCase(quiz) }

    fun insertQuestion(question: com.tpov.schoolquiz.data.database.entities.Question) =
        viewModelScope.launch { insertQuestionUseCase(question) }
}