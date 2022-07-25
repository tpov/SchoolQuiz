package com.tpov.schoolquiz.presentation.question

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.tpov.schoolquiz.data.RepositoryImpl
import com.tpov.schoolquiz.data.database.CrimeDatabase
import com.tpov.schoolquiz.data.database.entities.ApiQuestion
import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.data.database.entities.QuizDetail
import com.tpov.schoolquiz.domain.*
import kotlinx.coroutines.launch

class QuestionViewModel(database: CrimeDatabase) : ViewModel() {

    private val repository = RepositoryImpl(database)

    private val insertInfoQuestionUseCase = InsertInfoQuestionUseCase(repository)
    private val getInfoQuestionParamsUseCase = GetInfoQuestionParamsUseCase(repository)
    private val getQuestionUseCase = GetQuestionUseCase(repository)
    private val getInfoQuestionUseCase = GetInfoQuestionUseCase(repository)
    private val getQuizUseCase = GetQuizUseCase(repository)
    private val updateInfoQuestionUseCase = UpdateInfoQuestionUseCase(repository)
    private val updateQuizUseCase = UpdateQuizUseCase(repository)

    private val insertQuizUseCase = InsertQuizUseCase(repository)
    private val insertQuestionUseCase = InsertQuestionUseCase(repository)
    fun insertQuiz(quiz: Quiz) = viewModelScope.launch { insertQuizUseCase(quiz) }
    fun insertQuestion(question: Question) = viewModelScope.launch { insertQuestionUseCase(question) }

    private val insertApiQuestionUseCase = InsertApiQuestionUseCase(repository)
    private val getQuestionDayUseCase = GetQuestionDayUseCase(repository)
    private val updateApiQuestionUseCase = UpdateQuestionDayUseCase(repository)
    private var _allGetQuestionDay = MutableLiveData<List<ApiQuestion>>()
    var allGetQuestionDay: LiveData<List<ApiQuestion>> = _allGetQuestionDay
    @SuppressLint("NullSafeMutableLiveData")
    fun getQuestionDay() =
        viewModelScope.launch {
            _allGetQuestionDay.postValue(getQuestionDayUseCase())
        }
    fun updateQuestionDay(apiQuestion: ApiQuestion) =
        viewModelScope.launch {
            updateApiQuestionUseCase(apiQuestion)
        }
    fun insertApiQuestion(list: List<ApiQuestion>) = viewModelScope.launch {
        insertApiQuestionUseCase(list)
    }


    fun insertQuestion(updateAnswer: Boolean, insertQuiz: QuizDetail, idUser: String) =
        viewModelScope.launch {
            insertInfoQuestionUseCase(updateAnswer, insertQuiz, idUser)
        }

    fun getInfoQuestion(updateQuestion: Boolean, insertQuiz: QuizDetail, idUser: String) =
        viewModelScope.launch {
            getInfoQuestionParamsUseCase(updateQuestion, insertQuiz, idUser)
        }
    var getQuestion = getQuestionUseCase()
    var getInfoQuestion = getInfoQuestionUseCase()
    var getQuiz = getQuizUseCase()

    fun updateInfoQuestion(quizDetail: QuizDetail) = viewModelScope.launch {
        updateInfoQuestionUseCase(quizDetail)
    }
    fun updateQuiz(quiz: Quiz) = viewModelScope.launch {
        updateQuizUseCase(quiz)
    }


    class QuizModelFactory(private val database: CrimeDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
                @Suppress("UNCHECKED.CAST")
                return QuestionViewModel(database) as T
            }
            throw IllegalAccessException("Unknown ViewModelClass")
        }
    }
}
