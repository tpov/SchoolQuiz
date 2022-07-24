package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.QuizDetail

class SaveInfoQuestionUseCase(private val repository: Repository) {

    operator fun invoke(quizDetail: QuizDetail) = repository.insertInfoQuestion(quizDetail)
}