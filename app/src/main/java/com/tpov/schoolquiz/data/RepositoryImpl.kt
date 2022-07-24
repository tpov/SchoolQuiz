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

    override suspend fun resultQuiz(quiz: Quiz) {
        dao.updateFrontList(quiz)
    }

    override suspend fun getQuiz(): LiveData<List<Quiz>> {
        val allFrontList = MutableLiveData<List<Quiz>>()
        allFrontList.postValue(dao.getFrontList())
        return allFrontList
    }


    override suspend fun newQuiz(quiz: Quiz, question: Question) {
        if (quiz.nameQuestion == "GeoQuiz") {
            if (dao.getFrontListGeoQuiz("GeoQuiz").isEmpty()) dao.insertAllFrontList(quiz)
        } else dao.insertAllFrontList(quiz)

        if (question.idListNameQuestion == "GeoQuiz") {
            if (dao.getIdUserQuestion("GeoQuiz").isEmpty()) {
                dao.insertCrimeNewQuiz(question)
            }
        } else dao.insertCrimeNewQuiz(question)
    }

    override suspend fun getQuestionDay(): LiveData<List<ApiQuestion>> {
        var allGenerateQuestion = MutableLiveData<List<ApiQuestion>>()
        allGenerateQuestion.postValue(dao.getGenerateQuestion())
        return allGenerateQuestion
    }

    override suspend fun insertQuestionDay(list: List<ApiQuestion>) {
        dao.insertGenerateQuestion(list)
    }

    override suspend fun updateQuestionDay(question: ApiQuestion) {
        dao.updateGenerationQuestion(question)
    }


    override suspend fun getInfoQuestion(
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
        updateUnswer: Boolean,
        insertQuiz: QuizDetail,
        idUser: String
    ) {
        if (dao.getUpdateCrimeGeoQuiz(true, idUser).isEmpty()) dao.insertAll(insertQuiz)
    }

    override suspend fun updateInfoQuestion(quizDetail: QuizDetail) {
        dao.updateCrime(quizDetail)
    }

    override suspend fun getQuestion(): LiveData<List<Question>> {
        val allQuestionsQuiz = MutableLiveData<List<Question>>()
        allQuestionsQuiz.postValue(dao.getAllIdQuestion())
        return allQuestionsQuiz
    }
}