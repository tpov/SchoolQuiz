package com.tpov.schoolquiz.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.tpov.schoolquiz.data.database.QuizDao
import com.tpov.schoolquiz.data.database.entities.ApiQuestion
import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.data.database.entities.QuizDetail
import com.tpov.schoolquiz.domain.repository.Repository
import com.tpov.schoolquiz.presentation.mainactivity.quiz
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import kotlin.concurrent.thread

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

    override suspend fun updateQuiz(quiz: Quiz) {
        dao.updateQuiz(quiz)
    }

    override fun getInfoQuestionList() = dao.getQuizDetailList()

    override fun getQuiz() = dao.getQuiz()

    override fun getQuizList() = dao.getQuizList()
    override fun getQuestionListUseVase() = dao.getQuestionList()


    override fun insertQuiz(quiz: Quiz) {
        if (quiz.nameQuestion == "GeoQuiz") {
            if (dao.getListQuizByNameQuestion("GeoQuiz").isEmpty()) {
                dao.insertQuiz(quiz)
            }
        } else dao.insertQuiz(quiz)
    }

    override fun insertQuestion(question: Question) {
        Log.d("insertQuestion", "1 $question")
        if (question.idListNameQuestion == "GeoQuiz") {

            Log.d("insertQuestion", "2")
            if (dao.getListQuestionByIdUser(question.nameQuestion).isEmpty()) {
                Log.d("insertQuestion", "3")
                dao.insertQuestion(question)
            }
        } else dao.insertQuestion(question)
    }

    override fun getQuestionDay() = dao.getListApiQuestion()

    override fun insertQuestionDay(list: List<ApiQuestion>) {
        dao.insertListApiQuestion(list)
    }

    override suspend fun updateQuestionDay(question: ApiQuestion) {
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
        idUserQuestion: String
    ) {
        Log.d("v2.4", "$idUserQuestion")
        Log.d("v2.4", "${(dao.getListQuizDetailByUpdateANDIdUser(true, idUserQuestion))}")
        Log.d("v2.4", "${(dao.getListQuizDetailByUpdateANDIdUser(true, idUserQuestion).isEmpty())}")

        var quizDao = dao.getQuizDetail()
        var addQuiz = true

        val observer = Observer<List<QuizDetail>> {
            it.forEach { item ->
                if (item.idNameQuiz == idUserQuestion && !item.updateAnswer)
                    addQuiz = false
            }
            Log.d("v2.4", "observeForever, addQuiz = $addQuiz")
            Log.d("v2.4", "$insertQuiz")
        }

        quizDao.observeForever (observer)
        quizDao.removeObserver(observer)
        if (addQuiz) dao.insertQuizDetail(insertQuiz)
    }

    override suspend fun updateInfoQuestion(quizDetail: QuizDetail) {
        Log.d("v2.4", "dao. $quizDetail")

        coroutineScope {

            dao.updateQuizDetail(quizDetail)
        }
    }

    override fun getInfoQuestion(): LiveData<List<QuizDetail>> = dao.getQuizDetail()

    override fun getQuestion(): LiveData<List<Question>> = dao.getQuestion()

}