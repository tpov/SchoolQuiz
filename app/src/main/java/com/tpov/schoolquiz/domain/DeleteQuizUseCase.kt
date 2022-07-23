package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.entities.Quiz

class DeleteQuizUseCase(private val repository: Repository) {

    operator fun invoke(quiz: Quiz) = repository.deleteQuiz(quiz)

}