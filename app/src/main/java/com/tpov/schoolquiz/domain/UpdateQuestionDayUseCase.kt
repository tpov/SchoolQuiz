package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.ApiQuestion
import com.tpov.schoolquiz.domain.repository.Repository
import javax.inject.Inject

class UpdateQuestionDayUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(apiQuestion: ApiQuestion) = repository.updateQuestionDay(apiQuestion)
}