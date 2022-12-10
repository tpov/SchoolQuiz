package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.ApiQuestion
import com.tpov.schoolquiz.domain.repository.Repository
import javax.inject.Inject

class InsertApiQuestionUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(apiQuestion: List<ApiQuestion>) = repository.insertQuestionDay(apiQuestion)
}