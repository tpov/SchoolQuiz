package com.tpov.schoolquiz.presentation.splashscreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpov.schoolquiz.data.RepositoryImpl
import com.tpov.schoolquiz.data.database.QuizDatabase
import com.tpov.schoolquiz.data.database.entities.ApiQuestion
import com.tpov.schoolquiz.databinding.ActivitySplashScreenBinding
import com.tpov.schoolquiz.domain.GetQuestionDayUseCase
import com.tpov.schoolquiz.domain.InsertApiQuestionUseCase
import com.tpov.schoolquiz.domain.UpdateQuestionDayUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
class SplashScreenViewModel @Inject constructor(
        private val insertApiQuestionUseCase: InsertApiQuestionUseCase,
        private val getQuestionDayUseCase: GetQuestionDayUseCase,
        private val updateApiQuestionUseCase: UpdateQuestionDayUseCase,
): ViewModel() {

    lateinit var generateQuestion: ApiQuestion
    lateinit var generateQuestionNotNetwork: ApiQuestion
    var numQuestionNotDate = 0
    var numSystemDate = false
    var checkLoadApi = false
    var questionNotNetwork: String = ""
    var answerNotNetwork: String = ""
    var questionNotNetworkDate: String = ""
    var answerNotNetworkDate: String = ""
    private var numQuestionInList = 0
    var questionApiArray: Array<String>? = null
    var answerApiArray: Array<String>? = null

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

    private fun insertApiQuestion(list: List<ApiQuestion>) = viewModelScope.launch {
        insertApiQuestionUseCase(list)
    }

    fun loadDate(): String {
        Log.d("WorkManager", "Загрузка даты.")
        val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }

    fun loadQuestion() {
        Log.d("WorkManager", "Загрузка квеста.")
        val list = mutableListOf(
            ApiQuestion(null, "", "", "", "", ""),
            ApiQuestion(null, "", "", "", "", ""),
            ApiQuestion(null, "", "", "", "", ""),
            ApiQuestion(null, "", "", "", "", ""),
            ApiQuestion(null, "", "", "", "", ""),
            ApiQuestion(null, "", "", "", "", ""),
            ApiQuestion(null, "", "", "", "", ""),
            ApiQuestion(null, "", "", "", "", ""),
            ApiQuestion(null, "", "", "", "", ""),
            ApiQuestion(null, "", "", "", "", "")
        )

        for (i in 0..9) {
            if (questionApiArray!![i] != "") {
                Log.d("WorkManager", "Найдет не пустой квест, $i")
                var entityGenerateQuestion = getApiQuestion(i)
                list[i] = entityGenerateQuestion
                numQuestionInList++
            }
        }
        insertApiQuestion(list)
    }
}