package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.domain.repository.Repository
import javax.inject.Inject

class DeleteQuizUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(
        id: Int,
        deleteAnswerQuestion: Boolean,
        nameQuiz: String
    ) =
        repository.deleteQuiz(id, deleteAnswerQuestion, nameQuiz)
}