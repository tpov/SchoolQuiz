package com.tpov.schoolquiz.domain

import androidx.lifecycle.LiveData
import com.tpov.schoolquiz.data.database.entities.ApiQuestion
import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.data.database.entities.QuizDetail

interface Repository {

    //FrontActivity
    suspend fun deleteQuiz(deleteAnswerQuestion: Boolean, nameQuiz: String)

    suspend fun resultQuiz(quiz: Quiz)

    suspend fun getQuiz(): LiveData<List<Quiz>>

    suspend fun newQuiz(quiz: Quiz, question: Question)

    //SplashScreenActivity
    suspend fun getQuestionDay(): LiveData<List<ApiQuestion>>

    suspend fun insertQuestionDay(list: List<ApiQuestion>)

    suspend fun updateQuestionDay(question: ApiQuestion)

    //MainActivity
    suspend fun getInfoQuestion(
        updateAnswer: Boolean,
        insertQuiz: QuizDetail,
        idUser: String
    ): LiveData<List<QuizDetail>>

    suspend fun insertInfoQuestion(
        updateUnswer: Boolean,
        insertQuiz: QuizDetail,
        idUser: String
    )

    suspend fun updateInfoQuestion(quizDetail: QuizDetail)

    suspend fun getQuestion(): LiveData<List<Question>>
}