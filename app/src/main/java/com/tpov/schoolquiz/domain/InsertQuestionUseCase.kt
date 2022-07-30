package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.domain.repository.Repository

class InsertQuestionUseCase(private val repository: Repository) {
    suspend operator fun invoke(question: Question) = repository.insertQuestion(question)
}