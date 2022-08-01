package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.domain.repository.Repository
import javax.inject.Inject

class InsertQuestionUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(question: Question) = repository.insertQuestion(question)
}