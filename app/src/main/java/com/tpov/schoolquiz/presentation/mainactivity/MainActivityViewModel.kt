package com.tpov.schoolquiz.presentation.mainactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpov.schoolquiz.data.RepositoryImpl
import com.tpov.schoolquiz.data.database.QuizDatabase
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.domain.InsertQuestionUseCase
import com.tpov.schoolquiz.domain.InsertQuizUseCase
import kotlinx.coroutines.launch

class MainActivityViewModel(var database: QuizDatabase) : ViewModel() {

    private val repository = RepositoryImpl(database)

    private val insertQuizUseCase = InsertQuizUseCase(repository)
    private val insertQuestionUseCase = InsertQuestionUseCase(repository)

    fun insertQuiz(quiz: Quiz) = viewModelScope.launch { insertQuizUseCase(quiz) }

    fun insertQuestion(question: com.tpov.schoolquiz.data.database.entities.Question) =
        viewModelScope.launch { insertQuestionUseCase(question) }

}