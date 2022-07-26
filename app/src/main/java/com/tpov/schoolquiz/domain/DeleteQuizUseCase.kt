package com.tpov.schoolquiz.domain

class DeleteQuizUseCase(private val repository: Repository) {
    suspend operator fun invoke(
        id: Int,
        deleteAnswerQuestion: Boolean,
        nameQuiz: String
    ) =
        repository.deleteQuiz(id, deleteAnswerQuestion, nameQuiz)
}