package com.tpov.schoolquiz.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import com.tpov.schoolquiz.data.database.QuizDao
import com.tpov.schoolquiz.data.database.QuizDatabase
import com.tpov.schoolquiz.data.database.entities.ApiQuestion
import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.data.database.entities.QuizDetail
import com.tpov.schoolquiz.domain.repository.Repository
import com.tpov.schoolquiz.presentation.mainactivity.MainActivity
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@InternalCoroutinesApi
class RepositoryImpl @Inject constructor(
    private val dao: QuizDao
) : Repository {

    override fun deleteQuiz(
        id: Int,
        deleteAnswerQuestion: Boolean,
        nameQuiz: String
    ) {
        dao.deleteQuizByNameQuestion(nameQuiz)
        if (deleteAnswerQuestion) {
            dao.deleteQuizDetailByIdNameQuiz(nameQuiz)
            dao.deleteQuestionByNameQuiz(nameQuiz)
        }
    }

    override fun updateQuiz(quiz: Quiz) {
        dao.updateQuiz(quiz)
    }

    override fun getInfoQuestionList() = dao.getQuizDetailList()

    override fun getQuiz() = dao.getQuiz()


    override fun insertQuiz(quiz: Quiz) {
        if (quiz.nameQuestion == "GeoQuiz") {
            if (dao.getListQuizByNameQuestion("GeoQuiz").isEmpty()) {
                dao.insertQuiz(quiz)
            }
        } else dao.insertQuiz(quiz)
    }

    override fun insertQuestion(question: Question) {
        if (question.idListNameQuestion == "GeoQuiz") {
            if (dao.getListQuestionByIdUser("GeoQuiz").isEmpty()) {
                dao.insertQuestion(question)
            }
        } else dao.insertQuestion(question)
    }

    override fun getQuestionDay() = dao.getListApiQuestion()

    override fun insertQuestionDay(list: List<ApiQuestion>) {
        dao.insertListApiQuestion(list)
    }

    override fun updateQuestionDay(question: ApiQuestion) {
        dao.updateApiQuestion(question)
    }


    override fun getInfoQuestionParams(
        updateAnswer: Boolean,
        insertQuiz: QuizDetail,
        idUser: String
    ): LiveData<List<QuizDetail>> {

        val updateAnswerMutableCrime = MutableLiveData<List<QuizDetail>>()
        updateAnswerMutableCrime.postValue(
            dao.getListQuizDetailByUpdateANDIdUser(
                updateAnswer,
                idUser
            )
        )

        //Из-за плавающей ошибки(не всегда принимаются данные) вызываем еще пару раз :)
        updateAnswerMutableCrime.postValue(
            dao.getListQuizDetailByUpdateANDIdUser(
                updateAnswer,
                idUser
            )
        )
        updateAnswerMutableCrime.postValue(
            dao.getListQuizDetailByUpdateANDIdUser(
                updateAnswer,
                idUser
            )
        )
        return updateAnswerMutableCrime
    }

    override fun insertInfoQuestion(
        updateAnswer: Boolean,
        insertQuiz: QuizDetail,
        idUser: String
    ) {
        if (dao.getListQuizDetailByUpdateANDIdUser(true, idUser).isEmpty()) dao.insertQuizDetail(
            insertQuiz
        )
    }

    override fun updateInfoQuestion(quizDetail: QuizDetail) {
        dao.updateQuizDetail(quizDetail)
    }

    override fun getInfoQuestion(): LiveData<List<QuizDetail>> = dao.getQuizDetail()

    override fun getQuestion(): LiveData<List<Question>> = dao.getQuestion()

}