package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.domain.repository.Repository

class GetInfoQuestionListUseCase(private val repository: Repository) {
    suspend operator fun invoke() = repository.getInfoQuestionList()

}