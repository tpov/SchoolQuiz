package com.tpov.geoquiz.activity.workers

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.*
import com.tpov.geoquiz.activity.api.ApiFactory
import com.tpov.geoquiz.database.MainViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*

@InternalCoroutinesApi
class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {
    private val apiService = ApiFactory.apiService
    private lateinit var outputData: Data

    override suspend fun doWork(): Result {
        Log.d("WorkManager", "Запущен воркер.")
        //Принимаем к-во запасных вопросов которые уже загружены
        val questionNum = inputData.getInt(QUESTION_NUM, 0)

        var getQuestionApiArray = arrayOf("", "", "", "", "", "", "", "", "", "")
        var getAnswerApiArray = arrayOf("", "", "", "", "", "", "", "", "", "")

        //Дозагружаем вопросов столько, сколько нужно что-бы было 10шт
        for (i in questionNum..9) {

                val apiList = apiService.getFullPriceList("1")[0]
                getQuestionApiArray[i] = apiList.getQuestion()!!
                getAnswerApiArray[i] = apiList.getAnswer()!!
            Log.d("WorkManager", "Загружен вопрос $i, ${getQuestionApiArray[i]}")
        }


        outputData = Data.Builder()
            .putStringArray(QUESTION, getQuestionApiArray)
            .putStringArray(ANSWER, getAnswerApiArray)
            .build()

        Log.d("WorkManager", "Воркер завершен, входные данные-  $questionNum")
        return Result.success(outputData)
    }

    companion object {
        const val NAME = "RetrofitDataWorker"
        const val QUESTION_NUM = "question_num"
        const val QUESTION = "question"
        const val ANSWER = "answer"

        @SuppressLint("RestrictedApi")
        fun makeRequest(numQuestionNotData: Int): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().apply {
                setInputData(workDataOf(QUESTION_NUM to numQuestionNotData))
                setConstraints(makeConstraints())
                Log.d("WorkManager", "Воркер получил данные $numQuestionNotData")
            }.build()
        }

        private fun makeConstraints(): Constraints {
            return Constraints.Builder()
                .setRequiresCharging(true)
                .build()
        }
    }
}