package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.entities.Question

class LoadQuestionDayUseCase(private val repository: Repository) {
    operator fun invoke(systemDate: String) = repository.loadQuestionDay(systemDate)
}