package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.entities.QuizDetail

class SaveInfoQuestionUseCase(private val repository: Repository) {

    operator fun invoke(quizDetail: QuizDetail) = repository.saveInfoQuestion(quizDetail)
}