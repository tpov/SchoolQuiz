package com.tpov.geoquiz.activity.workers

import android.accounts.NetworkErrorException
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.*
import com.tpov.geoquiz.activity.api.ApiFactory
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.concurrent.TimeUnit

@InternalCoroutinesApi
class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {
    private val apiService = ApiFactory.apiService
    private lateinit var outputData: Data
    private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(context)
    }

    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result {

        Log.d("WorkManager", "Запущен воркер.")
        //Принимаем к-во запасных вопросов которые уже загружены
        val questionNum = inputData.getInt(QUESTION_NUM, 8)
        Log.d("WorkManager", "Данные в воркере: $questionNum")

        var getQuestionApiArray = arrayOf("", "", "", "", "", "", "", "", "", "")
        var getAnswerApiArray = arrayOf("", "", "", "", "", "", "", "", "", "")

        try {
            apiService.getFullPriceList("1")[0]
        } catch (e: Exception) {
            Result.success()
        }

        //Дозагружаем вопросов столько, сколько нужно что-бы было 10шт
        for (i in questionNum..9) {

            val apiList = apiService.getFullPriceList("1")[0]
            getQuestionApiArray[i] = apiList.getQuestion()!!
            getAnswerApiArray[i] = apiList.getAnswer()!!
            Log.d("WorkManager", "Загружен вопрос: $i, ${getQuestionApiArray[i]}")

            val intent = Intent("loaded").apply {
                putExtra("percent", ((i + 1) * 10))
            }
            localBroadcastManager.sendBroadcast(intent)
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
        const val CHANNEL_ID = "1"

        @SuppressLint("RestrictedApi")
        fun makeRequest(numQuestionNotData: Int): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().apply {
                setInputData(workDataOf(QUESTION_NUM to numQuestionNotData))
                Log.d("WorkManager", "Воркер получил данные $numQuestionNotData")
            }.build()
        }
    }
}