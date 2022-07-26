package com.tpov.schoolquiz.domain

class GetInfoQuestionListUseCase(private val repository: Repository) {
    operator fun invoke() = repository.getInfoQuestionList()

}