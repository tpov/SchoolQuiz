package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.domain.repository.Repository
import javax.inject.Inject

class UpdateQuizUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(quiz: Quiz) = repository.updateQuiz(quiz)
}