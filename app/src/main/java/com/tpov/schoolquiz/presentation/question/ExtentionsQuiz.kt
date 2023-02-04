package com.tpov.schoolquiz.presentation.question

import android.util.Log
import com.tpov.schoolquiz.data.model.ListQuestion
import com.tpov.schoolquiz.data.model.ListQuestionInfo
import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.data.database.entities.QuizDetail
import com.tpov.shoppinglist.utils.TimeManager
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
fun QuestionActivity.quiz(it: Quiz) =
    Quiz(
        it.id!!,
        it.nameQuestion,
        viewModel.loadUserName(it.nameQuestion),
        it.data,
        viewModel.loadStarsFun(it.nameQuestion),
        viewModel.quizListHQVar.size,
        ((it.numA) + 1),
        viewModel.quizListHardQuestion.size,
        it.starsAll + viewModel.percentPoints
    )

fun listQuestionInfo(it: Question) =
    ListQuestionInfo(
        it.id!!,
        it.nameQuestion,
        it.answerQuestion,
        it.typeQuestion,
        it.idListNameQuestion
    )

@InternalCoroutinesApi
fun listQuestion(it: QuizDetail) = ListQuestion(
    it.id!!,
    it.userName!!,
    it.idNameQuiz,
    it.data,
    it.numQuestion!!,
    it.persentPoints
)

@InternalCoroutinesApi
fun QuestionViewModel.insertQuizDetail(idUserQuestion: String): QuizDetail {
    return QuizDetail(
        null,
        idUserQuestion,
        userName,
        TimeManager.getCurrentTime(),
        null,
        null,
        0,
        false,
        0,
        0,
        0,
        3,
        null,
        0,
        0,
        true,
        null,
        null,
        null
    )
}

@InternalCoroutinesApi
fun QuestionViewModel.loadCrime(quizTable: QuizDetail) {
    this.codeAnswer = quizTable.codeAnswer
    this.codeMap = quizTable.codeMap
    this.currentIndexThis = quizTable.currentIndex
    this.isCheater = quizTable.isCheater
    this.updateAnswer = quizTable.updateAnswer
    this.constCurrentIndex = quizTable.constCurrentIndex
    this.points = quizTable.points
    this.percentPoints = quizTable.persentPoints
    this.cheatPoints = quizTable.cheatPoints
    this.charMap = quizTable.charMap
    this.i = quizTable.i
    this.j = quizTable.j
    this.idQuiz = quizTable.id!!

    this.leftAnswer = quizTable.leftUnswer
    this.numQuestion = quizTable.numQuestion
    this.numAnswer = quizTable.numAnswer
    this.currentIndex = currentIndexThis

    if (userName == "") {
        this.userName = quizTable.userName
    }

    Log.d("v2.4", "loadCrime: ${quizTable.id}")
    Log.d("v2.4", "loadCrime: $idQuiz")
    getQuestion
    Log.d("v2.4", "leftAnswer: $leftAnswer")
}

@InternalCoroutinesApi
fun QuestionViewModel.setQuizVar(getUpdateQuestion: Boolean, insertCrime: Boolean) {

    if (!insertCrime) {
        if (hardQuestion) {
            var crimeUpdate = QuizDetail(
                idQuiz,
                idUser,
                userName,
                TimeManager.getCurrentTime(),
                codeAnswer,
                codeMap,
                currentIndexThis,
                isCheater,
                constCurrentIndex,
                points,
                percentPoints,
                cheatPoints,
                charMap,
                i,
                j,
                false,
                leftAnswer,
                numQuestion,
                numAnswer
            )

            Log.d("v2.4", "updateInfoQuestion $crimeUpdate")
            updateInfoQuestion(crimeUpdate)
        } else {
            var crimeUpdate = QuizDetail(
                idQuiz,
                idUser,
                userName,
                TimeManager.getCurrentTime(),
                codeAnswer,
                codeMap,
                currentIndexThis,
                isCheater,
                constCurrentIndex,
                points,
                percentPoints,
                cheatPoints,
                charMap,
                i,
                j,
                getUpdateQuestion,
                leftAnswer,
                numQuestion,
                numAnswer
            )

            Log.d("v2.4", "updateInfoQuestion $crimeUpdate")
            updateInfoQuestion(crimeUpdate)
        }
    }
    Log.d("v2.4", "leftAnswer: $leftAnswer")
}