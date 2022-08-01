package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.domain.repository.Repository
import javax.inject.Inject

class InsertQuizUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(quiz: Quiz) = repository.insertQuiz(quiz)
}