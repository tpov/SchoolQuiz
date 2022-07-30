package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.QuizDetail
import com.tpov.schoolquiz.domain.repository.Repository

class InsertInfoQuestionUseCase(private val repository: Repository) {

    suspend operator fun invoke(updateAnswer: Boolean, insertQuiz: QuizDetail, idUser: String) =
        repository.insertInfoQuestion(updateAnswer, insertQuiz, idUser)
}