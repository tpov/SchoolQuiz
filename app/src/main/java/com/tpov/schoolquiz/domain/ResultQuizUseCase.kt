package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.entities.Quiz

class ResultQuizUseCase(private val repository: Repository) {

    operator fun invoke(quiz: Quiz) = repository.resultQuiz(quiz)
}