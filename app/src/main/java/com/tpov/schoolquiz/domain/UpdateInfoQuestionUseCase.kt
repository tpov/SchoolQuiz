package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.QuizDetail

class UpdateInfoQuestionUseCase(private val repository: Repository) {
    suspend operator fun invoke(quizDetail: QuizDetail) = repository.updateInfoQuestion(quizDetail)
}