package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.Quiz

class ShareQuizUseCase(private var repository: Repository) {
    operator fun invoke(quiz: Quiz) = repository.shareQuiz(quiz)
}