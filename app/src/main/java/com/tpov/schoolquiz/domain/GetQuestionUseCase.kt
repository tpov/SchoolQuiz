package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.domain.repository.Repository

class GetQuestionUseCase(private val repository: Repository) {
    operator fun invoke() = repository.getQuestion()
}