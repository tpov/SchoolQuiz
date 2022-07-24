package com.tpov.schoolquiz.domain

class LoadQuestionDayUseCase(private val repository: Repository) {
    operator fun invoke(systemDate: String) = repository.getQuestionDay(systemDate)
}