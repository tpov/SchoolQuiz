package com.tpov.schoolquiz.domain.repository

import androidx.lifecycle.LiveData
import com.tpov.schoolquiz.data.database.entities.ApiQuestion
import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.data.database.entities.QuizDetail

// TODO: 25.07.2022 LiveData -> Flow
interface Repository {

    //MainActivity
    fun deleteQuiz(id: Int,
                           deleteAnswerQuestion: Boolean,
                           nameQuiz: String)

    fun insertQuiz(quiz: Quiz)

    fun insertQuestion(question: Question)

    //SplashScreenActivity
    fun getQuestionDay(): List<ApiQuestion>

    fun insertQuestionDay(list: List<ApiQuestion>)

    fun updateQuestionDay(question: ApiQuestion)

    //QuizActivity
    fun getInfoQuestionParams(
        updateAnswer: Boolean,
        insertQuiz: QuizDetail,
        idUser: String
    ): LiveData<List<QuizDetail>>

    fun insertInfoQuestion(
        updateAnswer: Boolean,
        insertQuiz: QuizDetail,
        idUser: String
    )
    fun getQuiz(): LiveData<List<Quiz>>

    fun updateInfoQuestion(quizDetail: QuizDetail)

    fun getInfoQuestion(): LiveData<List<QuizDetail>>

    fun getQuestion(): LiveData<List<Question>>

    fun updateQuiz(quiz: Quiz)

    fun getInfoQuestionList(): List<QuizDetail>

}
