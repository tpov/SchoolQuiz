package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.data.database.entities.Quiz

class NewQuizUseCase(private val repository: Repository) {
    operator fun invoke(quiz: Quiz, question: Question) = repository.newQuiz(quiz, question)
}