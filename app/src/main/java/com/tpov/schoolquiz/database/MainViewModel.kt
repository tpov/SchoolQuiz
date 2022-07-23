package com.tpov.schoolquiz.database

import androidx.lifecycle.*
import com.tpov.schoolquiz.entities.QuizDetail
import com.tpov.schoolquiz.entities.Question
import com.tpov.schoolquiz.entities.ApiQuestion
import com.tpov.schoolquiz.entities.Quiz
import kotlinx.coroutines.launch

open class MainViewModel(database: CrimeDatabase) : ViewModel() {
    val dao = database.getCrimeDao()

    val allCrime = MutableLiveData<List<QuizDetail>>()
    val updateUnswerMutableCrime = MutableLiveData<List<QuizDetail>>()
    val allCrimeNewQuiz = MutableLiveData<List<Question>>()
    val allFrontList = MutableLiveData<List<Quiz>>()
    val allQuizAdapter: LiveData<List<Quiz>> = dao.getFrontListAdapter().asLiveData()

    val allGenerateQuestion = MutableLiveData<List<ApiQuestion>>()

    fun getCrime() = viewModelScope.launch {
        allCrime.postValue(dao.getCrime())
    }

    fun getQuestionCrimeNewQuiz() = viewModelScope.launch {
        allCrimeNewQuiz.postValue(dao.getAllIdQuestion())
    }

    fun getFrontList() = viewModelScope.launch {
        allFrontList.postValue(dao.getFrontList())
    }

    fun getGenerateQuestion() = viewModelScope.launch {
        allGenerateQuestion.postValue(dao.getGenerateQuestion())
    }

    fun deleteFrontList(id: Int, nameAnswerQuestion: Boolean, nameQuiz: String) =
        viewModelScope.launch {
            dao.deleteFrontList(nameQuiz)
            if (nameAnswerQuestion) {
                dao.deleteCrimeName(nameQuiz)
                dao.deleteCrimeNewQuizName(nameQuiz)
            }
        }

    fun insertAnswerCrime(
        updateUnswer: Boolean,
        insertQuiz: QuizDetail,
        idUser: String
    ) = viewModelScope.launch {
        if (!isUpdateGeoQuiz(idUser)) insertCrime(insertQuiz)
    }

    fun insertGenerationQuestion(entity: List<ApiQuestion>) = viewModelScope.launch {
        dao.insertGenerateQuestion(entity)
    }

    fun getUpdateAnswerCrime(updateUnswer: Boolean, insertQuiz: QuizDetail, idUser: String) =
        viewModelScope.launch {
            updateUnswerMutableCrime.postValue(dao.getUpdateCrimeGeoQuiz(updateUnswer, idUser))
            updateUnswerMutableCrime.postValue(dao.getUpdateCrimeGeoQuiz(updateUnswer, idUser))
            updateUnswerMutableCrime.postValue(dao.getUpdateCrimeGeoQuiz(updateUnswer, idUser))
        }

    fun getAllGenerateQuestion() = viewModelScope.launch {
        dao.getAllGenerateQuestion()
    }
    fun getDateGenerationQuestion(systemDate: String) = viewModelScope.launch {
        dao.getDateGenerationQuestion(systemDate)
    }

    fun deleteGenerateQuestion(generateId: Int) = viewModelScope.launch {
        dao.deleteGenerationQuestion(generateId)
    }
    fun insertCrime(note: QuizDetail) = viewModelScope.launch {
        dao.insertAll(note)
    }

    fun insertCrimeNewQuiz(name: Question) = viewModelScope.launch {
        if (name.idListNameQuestion == "GeoQuiz") {
            if (!isGeoQuiz()) {
                dao.insertCrimeNewQuiz(name)
            }
        } else dao.insertCrimeNewQuiz(name)
    }

    fun insertFrontList(quiz: Quiz) = viewModelScope.launch {
        if (quiz.nameQuestion == "GeoQuiz") {
            if (!isGeoQuizFront()) dao.insertAllFrontList(quiz)
        } else {
            dao.insertAllFrontList(quiz)
        }
    }

    fun updateCrime(quizDetail: QuizDetail) = viewModelScope.launch {
        dao.updateCrime(quizDetail)
    }

    fun updateFrontList(quiz: Quiz) = viewModelScope.launch {
        dao.updateFrontList(quiz)
    }

    fun updateGenerationQuestion(question: ApiQuestion) = viewModelScope.launch {
        dao.updateGenerationQuestion(question)
    }

    private suspend fun isGeoQuiz(): Boolean {
        return dao.getIdUserQuestion("GeoQuiz").isNotEmpty()
    }

    private suspend fun isUpdateGeoQuiz(idUser: String): Boolean {
        return dao.getUpdateCrimeGeoQuiz(true, idUser).isNotEmpty()
    }

    private suspend fun isGeoQuizFront(): Boolean {
        return dao.getFrontListGeoQuiz("GeoQuiz").isNotEmpty()
    }

    class MainViewModelFactory(private val database: CrimeDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED.CAST")
                return MainViewModel(database) as T
            }
            throw IllegalAccessException("Unknown ViewModelClass")
        }
    }
}