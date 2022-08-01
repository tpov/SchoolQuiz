package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.QuizDetail
import com.tpov.schoolquiz.domain.repository.Repository
import javax.inject.Inject

class UpdateInfoQuestionUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(quizDetail: QuizDetail) = repository.updateInfoQuestion(quizDetail)
}