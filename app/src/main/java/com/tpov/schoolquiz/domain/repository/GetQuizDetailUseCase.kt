package com.tpov.schoolquiz.domain.repository

import com.tpov.schoolquiz.data.database.entities.QuizDetail
import javax.inject.Inject

class getQuizDetailUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(updateQuestion: Boolean, insertQuiz: QuizDetail, idUser: String) =
        repository.getInfoQuestionParams(updateQuestion, insertQuiz, idUser)
}