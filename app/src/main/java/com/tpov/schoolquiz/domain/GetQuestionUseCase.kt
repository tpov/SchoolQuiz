package com.tpov.schoolquiz.domain

class GetQuestionUseCase(private val repository: Repository) {
    operator fun invoke() = repository.getQuestion()
}