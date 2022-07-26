package com.tpov.schoolquiz.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tpov.schoolquiz.data.database.QuizDatabase
import com.tpov.schoolquiz.data.database.entities.ApiQuestion
import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.data.database.entities.QuizDetail
import com.tpov.schoolquiz.domain.Repository

class RepositoryImpl(
    private val database: QuizDatabase
) : Repository {

    private val dao = database.getCrimeDao()

    override suspend fun deleteQuiz(id: Int,
                                    deleteAnswerQuestion: Boolean,
                                    nameQuiz: String) {
        dao.deleteQuizByNameQuestion(nameQuiz)
        if (deleteAnswerQuestion) {
            dao.deleteQuizDetailByIdNameQuiz(nameQuiz)
            dao.deleteQuestionByNameQuiz(nameQuiz)
        }
    }

    override suspend fun updateQuiz(quiz: Quiz) {
        dao.updateQuiz(quiz)
    }

    override fun getInfoQuestionList() = dao.getQuizDetailList()

    override fun getQuiz() = dao.getQuiz()


    override suspend fun insertQuiz(quiz: Quiz) {
        if (quiz.nameQuestion == "GeoQuiz") {
            if (dao.getListQuizByNameQuestion("GeoQuiz").isEmpty()) dao.insertQuiz(quiz)
        } else dao.insertQuiz(quiz)
    }

    override suspend fun insertQuestion(question: Question) {
        if (question.idListNameQuestion == "GeoQuiz") {
            if (dao.getListQuestionByIdUser("GeoQuiz").isEmpty()) {
                dao.insertQuestion(question)
            }
        } else dao.insertQuestion(question)
    }

    override fun getQuestionDay(): List<ApiQuestion> {
        var allGenerateQuestion = MutableLiveData<List<ApiQuestion>>()
        allGenerateQuestion.postValue(dao.getListApiQuestion())
        return dao.getListApiQuestion()
    }

    override suspend fun insertQuestionDay(list: List<ApiQuestion>) {
        dao.insertListApiQuestion(list)
    }

    override suspend fun updateQuestionDay(question: ApiQuestion) {
        dao.updateApiQuestion(question)
    }


    override suspend fun getInfoQuestionParams(
        updateUnswer: Boolean,
        insertQuiz: QuizDetail,
        idQuizUser: String
    ): LiveData<List<QuizDetail>> {

        val updateUnswerMutableCrime = MutableLiveData<List<QuizDetail>>()
        updateUnswerMutableCrime.postValue(dao.getListQuizDetailByUpdateANDIdUser(updateUnswer, idQuizUser))

        //Из-за плавающей ошибки(не всегда принимаются данные) вызываем еще пару раз :)
        updateUnswerMutableCrime.postValue(dao.getListQuizDetailByUpdateANDIdUser(updateUnswer, idQuizUser))
        updateUnswerMutableCrime.postValue(dao.getListQuizDetailByUpdateANDIdUser(updateUnswer, idQuizUser))
        return updateUnswerMutableCrime
    }

    override suspend fun insertInfoQuestion(
        updateAnswer: Boolean,
        insertQuiz: QuizDetail,
        idUser: String
    ) {
        if (dao.getListQuizDetailByUpdateANDIdUser(true, idUser).isEmpty()) dao.insertQuizDetail(insertQuiz)
    }

    override suspend fun updateInfoQuestion(quizDetail: QuizDetail) {
        dao.updateQuizDetail(quizDetail)
    }

    override fun getInfoQuestion(): LiveData<List<QuizDetail>> = dao.getQuizDetail()

    override fun getQuestion(): LiveData<List<Question>> = dao.getQuestion()

}