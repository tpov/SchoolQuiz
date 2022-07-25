package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.Quiz

class UpdateQuizUseCase(private val repository: Repository) {

    suspend operator fun invoke(quiz: Quiz) = repository.updateQuiz(quiz)
}