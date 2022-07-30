package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.ApiQuestion
import com.tpov.schoolquiz.domain.repository.Repository

class UpdateQuestionDayUseCase(private val repository: Repository) {
    suspend operator fun invoke(apiQuestion: ApiQuestion) = repository.updateQuestionDay(apiQuestion)
}