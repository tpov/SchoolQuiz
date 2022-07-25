package com.tpov.schoolquiz.domain

class GetQuizUseCase(private val repository: Repository) {
    operator fun invoke() = repository.getQuiz()
}