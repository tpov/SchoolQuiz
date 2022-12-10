package com.tpov.schoolquiz.domain

import com.tpov.schoolquiz.data.database.entities.QuizDetail
import com.tpov.schoolquiz.domain.repository.Repository
import javax.inject.Inject

class InsertInfoQuestionUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(updateAnswer: Boolean, insertQuiz: QuizDetail, idUser: String) =
        repository.insertInfoQuestion(updateAnswer, insertQuiz, idUser)
}