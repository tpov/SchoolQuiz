package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.entities.ApiQuestion
import com.tpov.schoolquiz.entities.Question
import com.tpov.schoolquiz.entities.Quiz
import com.tpov.schoolquiz.entities.QuizDetail

interface Repository {

    //FrontActivity
    fun deleteQuiz(quiz: Quiz)

    fun shareQuiz(quiz: Quiz)

    fun startQuiz(quiz: Quiz)

    fun resultQuiz(quiz: Quiz)

    fun newQuiz(quiz: Quiz, question: Question)

    //SplashScreenActivity
    fun loadQuestionDay(systemDate: String): ApiQuestion

    //MainActivity
    fun answerQuestion(quiz: Quiz, question: Question): Boolean

    fun getInfoQuestion(): QuizDetail

    fun saveInfoQuestion(quizDetail: QuizDetail)

    fun getQuestion(questionId: Int): Question
}