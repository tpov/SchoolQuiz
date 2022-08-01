package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.QuizDetail
import com.tpov.schoolquiz.domain.repository.Repository
import javax.inject.Inject

class GetInfoQuestionParamsUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(updateQuestion: Boolean, insertQuiz: QuizDetail, idUser: String) =
        repository.getInfoQuestionParams(updateQuestion, insertQuiz, idUser)
}