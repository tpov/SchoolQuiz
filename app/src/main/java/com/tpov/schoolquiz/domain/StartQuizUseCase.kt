package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.Quiz

class StartQuizUseCase(private val repository: Repository) {
    operator fun invoke(quiz: Quiz) = repository.startQuiz(quiz)
}