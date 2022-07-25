package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.ApiQuestion

class InsertApiQuestionUseCase(private val repository: Repository) {
    suspend operator fun invoke(apiQuestion: List<ApiQuestion>) = repository.insertQuestionDay(apiQuestion)
}