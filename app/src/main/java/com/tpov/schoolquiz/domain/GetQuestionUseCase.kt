package com.tpov.schoolquiz.domain

class GetQuestionUseCase(private val repository: Repository) {
    operator fun invoke(questionId: Int) = repository.getQuestion(questionId)
}