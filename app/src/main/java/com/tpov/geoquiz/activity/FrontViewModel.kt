package com.tpov.geoquiz.activity

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FrontViewModel(
    private var typeQuestion: Boolean,
    private var pA: Int,
    private val application: Application
) : ViewModel() {

    private var timer: CountDownTimer? = null
    private var percentAnswer: Int = 0

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _answerQuiz = MutableLiveData<Int>()
    val answerQuiz: LiveData<Int>
        get() = _answerQuiz

    private val _gameResult = MutableLiveData<Boolean>()
    val gameResult: LiveData<Boolean>
        get() = _gameResult


    private fun startTimer(typeAnswer: Boolean) {
        timer?.cancel()
        if (typeAnswer) {
            timer = object : CountDownTimer(
                getCurrentTimer(typeQuestion) * MILLIS_IN_SECONDS,
                MILLIS_IN_SECONDS
            ) {
                override fun onTick(millisUntilFinished: Long) {
                    _formattedTime.value = formatTime(millisUntilFinished)
                }

                override fun onFinish() {
                    _gameResult.value = false

                }
            }
            timer?.start()
        }
    }

    private fun getCurrentTimer(typeQuestion: Boolean): Int {
        return if (typeQuestion) TIME_HARD_QUESTION
        else TIME_LIGHT_QUESTION
    }

    fun startGame(time: Boolean) {
        startTimer(time)
    }

    fun updatePercentAnswer(leftAnswer: Int, numAnswer: Int) {
        if (numAnswer == 0) _answerQuiz.value = 0
        else _answerQuiz.value = (100 * numAnswer!! / (leftAnswer!! + numAnswer!!))
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60

        private const val TIME_HARD_QUESTION = 10
        private const val TIME_LIGHT_QUESTION = 20
    }

}