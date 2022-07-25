package com.tpov.schoolquiz.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tpov.schoolquiz.data.database.CrimeDatabase
import com.tpov.schoolquiz.data.database.entities.ApiQuestion
import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.data.database.entities.QuizDetail
import com.tpov.schoolquiz.domain.Repository

class RepositoryImpl(
    private val database: CrimeDatabase
) : Repository {

    private val dao = database.getCrimeDao()

    override suspend fun deleteQuiz(deleteAnswerQuestion: Boolean, nameQuiz: String) {
        dao.deleteFrontList(nameQuiz)
        if (deleteAnswerQuestion) {
            dao.deleteCrimeName(nameQuiz)
            dao.deleteCrimeNewQuizName(nameQuiz)
        }
    }

    override suspend fun updateQuiz(quiz: Quiz) {
        dao.updateFrontList(quiz)
    }

    override fun getQuiz() = dao.getFrontList()


    override suspend fun insertQuiz(quiz: Quiz) {
        if (quiz.nameQuestion == "GeoQuiz") {
            if (dao.getFrontListGeoQuiz("GeoQuiz").isEmpty()) dao.insertAllFrontList(quiz)
        } else dao.insertAllFrontList(quiz)
    }

    override suspend fun insertQuestion(question: Question) {
        if (question.idListNameQuestion == "GeoQuiz") {
            if (dao.getIdUserQuestion("GeoQuiz").isEmpty()) {
                dao.insertCrimeNewQuiz(question)
            }
        } else dao.insertCrimeNewQuiz(question)
    }

    override fun getQuestionDay(): List<ApiQuestion> {
        var allGenerateQuestion = MutableLiveData<List<ApiQuestion>>()
        allGenerateQuestion.postValue(dao.getGenerateQuestion())
        return dao.getGenerateQuestion()
    }

    override suspend fun insertQuestionDay(list: List<ApiQuestion>) {
        dao.insertGenerateQuestion(list)
    }

    override suspend fun updateQuestionDay(question: ApiQuestion) {
        dao.updateGenerationQuestion(question)
    }


    override suspend fun getInfoQuestionParams(
        updateUnswer: Boolean,
        insertQuiz: QuizDetail,
        idQuizUser: String
    ): LiveData<List<QuizDetail>> {

        val updateUnswerMutableCrime = MutableLiveData<List<QuizDetail>>()
        updateUnswerMutableCrime.postValue(dao.getUpdateCrimeGeoQuiz(updateUnswer, idQuizUser))

        //Из-за плавающей ошибки(не всегда принимаются данные) вызываем еще пару раз :)
        updateUnswerMutableCrime.postValue(dao.getUpdateCrimeGeoQuiz(updateUnswer, idQuizUser))
        updateUnswerMutableCrime.postValue(dao.getUpdateCrimeGeoQuiz(updateUnswer, idQuizUser))
        return updateUnswerMutableCrime
    }

    override suspend fun insertInfoQuestion(
        updateAnswer: Boolean,
        insertQuiz: QuizDetail,
        idUser: String
    ) {
        if (dao.getUpdateCrimeGeoQuiz(true, idUser).isEmpty()) dao.insertAll(insertQuiz)
    }

    override suspend fun updateInfoQuestion(quizDetail: QuizDetail) {
        dao.updateCrime(quizDetail)
    }

    override fun getInfoQuestion(): LiveData<List<QuizDetail>> = dao.getAllCrime()

    override fun getQuestion(): LiveData<List<Question>> = dao.getAllIdQuestion()

}