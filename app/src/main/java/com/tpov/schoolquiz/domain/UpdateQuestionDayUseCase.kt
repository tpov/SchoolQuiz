package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.ApiQuestion

class UpdateQuestionDayUseCase(private val repository: Repository) {
    suspend operator fun invoke(apiQuestion: ApiQuestion) = repository.updateQuestionDay(apiQuestion)
}