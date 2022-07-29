package com.tpov.schoolquiz.presentation.question

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
        it.starsAll + viewModel.persentPoints
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
fun QuestionActivity.listQuestion(it: QuizDetail) = ListQuestion(
    it.id!!,
    it.userName!!,
    it.idNameQuiz,
    it.data,
    it.numQuestion!!,
    it.persentPoints
)

@InternalCoroutinesApi
fun QuestionViewModel.insertQuizDetail(idName: String): QuizDetail {
    return QuizDetail(
        null,
        idName,
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
    codeAnswer = quizTable.codeAnswer
    codeMap = quizTable.codeMap
    currentIndexThis = quizTable.currentIndex
    isCheater = quizTable.isCheater
    updateAnswer = quizTable.updateAnswer
    constCurrentIndex = quizTable.constCurrentIndex
    points = quizTable.points
    persentPoints = quizTable.persentPoints
    cheatPoints = quizTable.cheatPoints
    charMap = quizTable.charMap
    i = quizTable.i
    j = quizTable.j
    idCrime = quizTable.id!!

    leftAnswer = quizTable.leftUnswer
    numQuestion = quizTable.numQuestion
    numAnswer = quizTable.numAnswer
    currentIndex = currentIndexThis

    if (userName == "") {
        userName = quizTable.userName
    }

    getQuestion
}

@InternalCoroutinesApi
fun QuestionViewModel.setCrimeVar(getUpdateQuestion: Boolean, insertCrime: Boolean) {

    if (!insertCrime) {
        if (hardQuestion) {
            var crimeUpdate = QuizDetail(
                idCrime,
                idUser,
                userName,
                TimeManager.getCurrentTime(),
                codeAnswer,
                codeMap,
                currentIndexThis,
                isCheater,
                constCurrentIndex,
                points,
                persentPoints,
                cheatPoints,
                charMap,
                i,
                j,
                false,
                leftAnswer,
                numQuestion,
                numAnswer
            )
            updateInfoQuestion(crimeUpdate)
        } else {
            var crimeUpdate = QuizDetail(
                idCrime,
                idUser,
                userName,
                TimeManager.getCurrentTime(),
                codeAnswer,
                codeMap,
                currentIndexThis,
                isCheater,
                constCurrentIndex,
                points,
                persentPoints,
                cheatPoints,
                charMap,
                i,
                j,
                getUpdateQuestion,
                leftAnswer,
                numQuestion,
                numAnswer
            )
            updateInfoQuestion(crimeUpdate)
        }
    }
}