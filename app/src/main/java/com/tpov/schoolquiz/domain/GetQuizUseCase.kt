package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.domain.repository.Repository
import javax.inject.Inject

class GetQuizUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke() = repository.getQuiz()
}