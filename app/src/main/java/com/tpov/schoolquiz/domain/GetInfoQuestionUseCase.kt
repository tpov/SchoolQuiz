package com.tpov.schoolquiz.domain

class GetInfoQuestionUseCase(private val repository: Repository) {
    operator fun invoke() = repository.getInfoQuestion()
}