package com.tpov.schoolquiz.presentation.question

import com.tpov.schoolquiz.data.database.entities.QuizDetail
import com.tpov.shoppinglist.utils.TimeManager
import kotlinx.coroutines.InternalCoroutinesApi

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