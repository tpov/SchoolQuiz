package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.domain.repository.Repository
import javax.inject.Inject

class GetQuestionDayUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke() = repository.getQuestionDay()
}