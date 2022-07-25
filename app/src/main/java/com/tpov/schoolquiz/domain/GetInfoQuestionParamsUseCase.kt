package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.QuizDetail

class GetInfoQuestionParamsUseCase(private val repository: Repository) {
    suspend operator fun invoke(updateQuestion: Boolean, insertQuiz: QuizDetail, idUser: String) =
        repository.getInfoQuestionParams(updateQuestion, insertQuiz, idUser)
}