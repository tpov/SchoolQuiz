package com.tpov.geoquiz.database

import androidx.lifecycle.*
import com.tpov.geoquiz.entities.Crime
import com.tpov.geoquiz.entities.CrimeNewQuiz
import com.tpov.geoquiz.entities.EntityGenerateQuestion
import com.tpov.geoquiz.entities.FrontList
import kotlinx.coroutines.launch

open class MainViewModel(database: CrimeDatabase) : ViewModel() {
    val dao = database.getCrimeDao()

    val allCrime = MutableLiveData<List<Crime>>()
    val updateUnswerMutableCrime = MutableLiveData<List<Crime>>()
    val allCrimeNewQuiz = MutableLiveData<List<CrimeNewQuiz>>()
    val allFrontList = MutableLiveData<List<FrontList>>()
    val allFrontListAdapter: LiveData<List<FrontList>> = dao.getFrontListAdapter().asLiveData()

    val allGenerateQuestion = MutableLiveData<List<EntityGenerateQuestion>>()

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
        insertQuiz: Crime,
        idUser: String
    ) = viewModelScope.launch {
        if (!isUpdateGeoQuiz(idUser)) insertCrime(insertQuiz)
    }

    fun insertGenerationQuestion(entity: List<EntityGenerateQuestion>) = viewModelScope.launch {
        dao.insertGenerateQuestion(entity)
    }

    fun getUpdateAnswerCrime(updateUnswer: Boolean, insertQuiz: Crime, idUser: String) =
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
    fun insertCrime(note: Crime) = viewModelScope.launch {
        dao.insertAll(note)
    }

    fun insertCrimeNewQuiz(name: CrimeNewQuiz) = viewModelScope.launch {
        if (name.idListNameQuestion == "GeoQuiz") {
            if (!isGeoQuiz()) {
                dao.insertCrimeNewQuiz(name)
            }
        } else dao.insertCrimeNewQuiz(name)
    }

    fun insertFrontList(frontList: FrontList) = viewModelScope.launch {
        if (frontList.nameQuestion == "GeoQuiz") {
            if (!isGeoQuizFront()) dao.insertAllFrontList(frontList)
        } else {
            dao.insertAllFrontList(frontList)
        }
    }

    fun updateCrime(crime: Crime) = viewModelScope.launch {
        dao.updateCrime(crime)
    }

    fun updateFrontList(frontList: FrontList) = viewModelScope.launch {
        dao.updateFrontList(frontList)
    }

    fun updateGenerationQuestion(question: EntityGenerateQuestion) = viewModelScope.launch {
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