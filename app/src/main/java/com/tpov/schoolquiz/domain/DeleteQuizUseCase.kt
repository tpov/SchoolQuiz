package com.tpov.schoolquiz.domain

class DeleteQuizUseCase(private val repository: Repository) {
    suspend operator fun invoke(deleteAnswerQuestion: Boolean, nameQuiz: String) =
        repository.deleteQuiz(deleteAnswerQuestion, nameQuiz)
}