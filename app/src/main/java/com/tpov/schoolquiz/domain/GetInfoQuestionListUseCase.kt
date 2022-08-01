package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.domain.repository.Repository
import javax.inject.Inject

class GetInfoQuestionListUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke() = repository.getInfoQuestionList()

}