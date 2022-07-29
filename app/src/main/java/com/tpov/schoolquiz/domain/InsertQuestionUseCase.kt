package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.Question

class InsertQuestionUseCase(private val repository: Repository) {
    suspend operator fun invoke(question: Question) = repository.insertQuestion(question)
}