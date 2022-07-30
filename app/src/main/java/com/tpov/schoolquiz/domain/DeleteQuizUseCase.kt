package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.domain.repository.Repository

class DeleteQuizUseCase(private val repository: Repository) {
    suspend operator fun invoke(
        id: Int,
        deleteAnswerQuestion: Boolean,
        nameQuiz: String
    ) =
        repository.deleteQuiz(id, deleteAnswerQuestion, nameQuiz)
}