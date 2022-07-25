package com.tpov.schoolquiz.domain

class GetQuestionDayUseCase(private val repository: Repository) {
    suspend operator fun invoke() = repository.getQuestionDay()
}