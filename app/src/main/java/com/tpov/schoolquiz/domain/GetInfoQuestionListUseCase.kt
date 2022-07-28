package com.tpov.schoolquiz.domain

class GetInfoQuestionListUseCase(private val repository: Repository) {
    suspend operator fun invoke() = repository.getInfoQuestionList()

}