package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.domain.repository.Repository

class GetQuestionDayUseCase(private val repository: Repository) {
    suspend operator fun invoke() = repository.getQuestionDay()
}