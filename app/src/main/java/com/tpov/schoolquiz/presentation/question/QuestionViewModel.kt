package com.tpov.schoolquiz.presentation.question

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.*
import com.tpov.schoolquiz.R
import com.tpov.schoolquiz.data.model.ListQuestion
import com.tpov.schoolquiz.data.model.ListQuestionInfo
import com.tpov.schoolquiz.data.model.ListQuiz
import com.tpov.schoolquiz.data.RepositoryImpl
import com.tpov.schoolquiz.data.database.QuizDatabase
import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.data.database.entities.QuizDetail
import com.tpov.schoolquiz.domain.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlin.random.Random

class QuestionViewModel(var database: QuizDatabase) : ViewModel() {
    private var timer: CountDownTimer? = null

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _answerQuiz = MutableLiveData<Int>()
    val answerQuiz: LiveData<Int>
        get() = _answerQuiz

    private val _gameResult = MutableLiveData<Boolean>()
    val gameResult: LiveData<Boolean>
        get() = _gameResult

    var listQuestion = mutableListOf<ListQuestion>()
    var listQuestionInfo = mutableListOf<ListQuestionInfo>()
    var listQuiz = mutableListOf<ListQuiz>()

    var quizList = mutableListOf<com.tpov.schoolquiz.data.model.Quiz>()
    var quizListHardQuestion = mutableListOf<com.tpov.schoolquiz.data.model.Quiz>()
    var quizListHQVar = mutableListOf<com.tpov.schoolquiz.data.model.Quiz>()

    var mapAnswer: MutableMap<Int, Boolean> = mutableMapOf()
    val TAG = "QuestionViewModel"

    //Начальные данные для квеста
    var numQuestion: Int? = 0       //WTF
    var numAnswer: Int? = 0
    var leftAnswer: Int? = 0        //WTF
    var codeAnswer: String? = ""
    var codeMap: String? = ""
    var currentIndexThis: Int = -1
    var isCheater: Boolean = false
    var updateAnswer: Boolean = true
    var insertCrime: Boolean = true
    var insertCrimeNewQuiz: Boolean = true
    var constCurrentIndex: Int = 0
    var points: Int = 0
    var persentPoints: Int = 0
    var cheatPoints: Int = 3
    var charMap: String? = ""
    var i: Int = 0
    var j: Int = 0
    var idCrime = 0
    var userName: String? = ""
    var idUser = ""
    var hardQuestion = false
    var updateFrontList = 0
    var stars = 0
    var persentAnswer = 0
    var currentIndex = 0
    var checkTimer = false

    private var s = 0
    private var d = 0
    private var f = 0
    private var h = 0
    private var l = 0

    private val repository = RepositoryImpl(database)

    private val insertInfoQuestionUseCase = InsertInfoQuestionUseCase(repository)
    private val getInfoQuestionParamsUseCase = GetInfoQuestionParamsUseCase(repository)
    private val getQuestionUseCase = GetQuestionUseCase(repository)
    private val getInfoQuestionUseCase = GetInfoQuestionUseCase(repository)
    private val getQuizUseCase = GetQuizUseCase(repository)
    private val updateInfoQuestionUseCase = UpdateInfoQuestionUseCase(repository)
    private val updateQuizUseCase = UpdateQuizUseCase(repository)
    private val getInfoQuestionListUseCase = GetInfoQuestionListUseCase(repository)

    private val insertQuizUseCase = InsertQuizUseCase(repository)
    private val insertQuestionUseCase = InsertQuestionUseCase(repository)
    fun insertQuiz(quiz: Quiz) = viewModelScope.launch { insertQuizUseCase(quiz) }
    fun insertQuestion(question: Question) =
        viewModelScope.launch { insertQuestionUseCase(question) }

    private val deleteQuizUseCase = DeleteQuizUseCase(repository)
    fun deleteQuiz(id: Int, deleteQuestion: Boolean, nameQuiz: String) = viewModelScope.launch {
        deleteQuizUseCase(id, deleteQuestion, nameQuiz)
    }

    private fun insertQuestion(updateAnswer: Boolean, insertQuiz: QuizDetail, idUser: String) =
        viewModelScope.launch {
            insertInfoQuestionUseCase(updateAnswer, insertQuiz, idUser)
        }

    private fun getInfoQuestion(updateQuestion: Boolean, insertQuiz: QuizDetail, idUser: String) =
        viewModelScope.launch {
            getInfoQuestionParamsUseCase(updateQuestion, insertQuiz, idUser)
        }

    var getQuestion: LiveData<List<Question>> = getQuestionUseCase()
    var getInfoQuestion: LiveData<List<QuizDetail>> = getInfoQuestionUseCase()
    lateinit var getInfoQuestionList: List<QuizDetail>
    var getQuiz: LiveData<List<Quiz>> = getQuizUseCase()

    init {
        viewModelScope.launch {
            getInfoQuestionList = getInfoQuestionListUseCase()
        }
    }

    private var _getInfoQuestionLiveData = MutableLiveData<List<QuizDetail>>()
    var getInfoQuestionLiveData: LiveData<List<QuizDetail>> = _getInfoQuestionLiveData

    fun updateInfoQuestion(quizDetail: QuizDetail) = viewModelScope.launch {
        updateInfoQuestionUseCase(quizDetail)
    }

    @InternalCoroutinesApi
    fun updateQuiz(quiz: Quiz) = viewModelScope.launch {
        updateQuizUseCase(quiz)
    }

    @InternalCoroutinesApi
    fun getUpdateCrime(idUser: String) {
        insertQuestion(true, insertQuizDetail(idUser), idUser)
        _getInfoQuestionLiveData.postValue(getInfoQuestionList)
        getInfoQuestion(true, insertQuizDetail(idUser), idUser)
    }

    private var _cheatPointsLifeLiveData = MutableLiveData<String>()
    var cheatPointsLiveData: LiveData<String> = _cheatPointsLifeLiveData
    private var _cheatButtonLiveData = MutableLiveData<Boolean>()
    var cheatButtonLiveData: LiveData<Boolean> = _cheatButtonLiveData

    private fun useCheat() {
        cheatPoints -= 1
        _cheatPointsLifeLiveData.postValue("Life = $cheatPoints")
        if (cheatPoints == 0) {
            _cheatButtonLiveData.postValue(false)
        }
        isCheater = false
    }

    fun prefButton() {

        if (currentIndex == 0) {
            showToast(R.string.null_toast)
            springAnim(false)
        } else {
            moveToPref()
        }
        checkBlock()
    }

    var x = 0
    private var _moveToPrevLiveData = MutableLiveData<Int>()
    var moveToPrevLiveData: LiveData<Int> = _moveToPrevLiveData

    private fun moveToPref() {
        _moveToPrevLiveData.postValue(x++)
    }


    fun nextButton() {
        if (currentIndex == numAnswer!! - 1) {
            showToast(R.string.null_toast)
            springAnim(true)
        } else {
            moveToNext()
        }
        checkBlock()
    }

    @InternalCoroutinesApi
    fun trueButton() {
        //Проверка нужно ли показывать следующий вопрос после нажатия на кнопку
        if (!updateAnswer) {
            checkBlockMap()
            checkBlock()
            coderBlockMap()
            checkAnswer(true)
            constCurrentIndex += 1
            resultTextView(points)
            if (constCurrentIndex == numAnswer) {
                result(points)
            } else {
                setCrimeVar(true, false)
            }
        } else {
            checkBlockMap()
            checkBlock()
            coderBlockMap()
            checkAnswer(true)
            constCurrentIndex += 1
            resultTextView(points)

            if (currentIndex == numAnswer!! - 1) {
                showToast(R.string.null_toast)
                springAnim(true)
            } else {
                moveToNext()
            }
            checkBlock()
            if (constCurrentIndex == numAnswer) {
                result(points)
            } else {
                setCrimeVar(true, false)
            }
        }
        if (constCurrentIndex != numAnswer) {
            updatePersentView(leftAnswer!!, persentPoints)
        }
    }

    @InternalCoroutinesApi
    fun falseButton() {
        //Проверка нужно ли показывать следующий вопрос после нажатия на кнопку
        if (!updateAnswer) {
            checkBlockMap()
            checkBlock()
            coderBlockMap()
            checkAnswer(false)
            constCurrentIndex += 1
            resultTextView(points)
            if (constCurrentIndex == numAnswer) {
                result(points)
            } else {
                setCrimeVar(true, false)
            }
        } else {
            checkBlockMap()
            checkBlock()
            coderBlockMap()
            checkAnswer(false)
            constCurrentIndex += 1
            resultTextView(points)

            if (currentIndex == numAnswer!! - 1) {
                showToast(R.string.null_toast)
                springAnim(true)
            } else {
                moveToNext()
            }
            checkBlock()
        }
        if (constCurrentIndex == numAnswer) {
            result(points)
        } else {
            setCrimeVar(getUpdateQuestion = true, insertCrime = false)
        }
        if (constCurrentIndex != numAnswer) {
            updatePersentView(leftAnswer!!, persentPoints)
        }
    }

    private var _springAnim = MutableLiveData<Boolean>()
    var springAnim: LiveData<Boolean> = _springAnim

    private fun springAnim(b: Boolean) {
        _springAnim.postValue(b)
    }

    @InternalCoroutinesApi
    fun endTimer() {
        if (!updateAnswer) {
            checkBlockMap()
            checkBlock()
            coderBlockMap()
            checkAnswer(intToBool(Random.nextInt(0, 1)))
            constCurrentIndex += 1
            resultTextView(points)
            if (constCurrentIndex == numAnswer) {
                result(points)
            } else {
                setCrimeVar(true, false)
            }
        } else {
            checkBlockMap()
            checkBlock()
            coderBlockMap()
            checkAnswer(intToBool(Random.nextInt(0, 1)))

            constCurrentIndex += 1
            resultTextView(points)

            if (currentIndex == numAnswer!! - 1) {
                showToast(R.string.null_toast)
            } else {
                moveToNext()
                updateQuestion()
            }
            checkBlock()
        }
        if (constCurrentIndex == numAnswer) {
            result(points)
        } else {
            setCrimeVar(true, false)
        }
        if (constCurrentIndex != numAnswer) {
            updatePersentView(leftAnswer!!, persentPoints)
        }
        checkTimer = false
    }

    private var _moveToNextLiveData = MutableLiveData<Int>()
    var moveToNextLiveData: LiveData<Int> = _moveToNextLiveData

    //Ильтераторы с помощю которых при их изменении активируется ливдата
    private var z = 0

    private fun moveToNext() {
        _moveToNextLiveData.postValue(z++)
    }

    private fun intToBool(nextInt: Int): Boolean = nextInt == 1

    fun getHardQuestion(stars: Int) = stars >= 100

    //Когда отвечаете на вопрос квеста, все ответы сохраняются в виде одной строки с набором символов 0,1,2
    //где 0 - не отвеченный вопрос, 1 - не верный ответ, 2 - верный.
    //Это позволяет восстановить сессию, подсчитывать результаты, показывать список вопросов с результатами
    fun createCodeAnswer() {
        codeAnswer = ""
        repeat(quizList.size) {
            codeAnswer += '0'
        }
    }

    @InternalCoroutinesApi
    fun result(points: Int) {
        persentPoints = if (hardQuestion) (points * 20 / numQuestion!!) + 100
        else points * 100 / numQuestion!!
        showToast(persentPoints)
        updatePersentView(leftAnswer!!, persentPoints)
        setCrimeVar(getUpdateQuestion = false, insertCrime = false)

        loadFrontList()
        checkTimer = false
        loadTimer()
    }

    //false в маппере значит, что мы ответили на этот вопрос
    private fun checkBlockMap() {
        mapAnswer[currentIndexThis] = false
        leftAnswer = leftAnswer!!.minus(1)
        updatePersentView(leftAnswer!!, persentPoints)
        coderBlockMap()
    }

    //Переобразовывам данные маппера в строку для сохранения в бд
    fun coderBlockMap() {
        codeMap = ""
        for (i in 0 until numAnswer!!) {
            if (mapAnswer[i] == false) {
                charMap = "0"
                codeMap = "$codeMap$charMap"
            } else {
                charMap = "1"
                codeMap = "$codeMap$charMap"
            }
        }
        i = 0
    }

    //Переобразовываем из строки в данные маппера
    fun decoderBlockMap() {
        for (i in 0 until numAnswer!!) {
            mapAnswer[j] = codeMap!![j] == '1'
            j++
        }
        j = 0
    }

    private fun resultTextView(points: Int) {
        log("resultTextView")
        persentPoints = points * 100 / numAnswer!!
        updatePersentView(leftAnswer!!, persentPoints)

        checkTimer = false
        loadPBAnswer()
    }

    //Создаем строку из символов 0,1,2 в зависимости правильный, не правильный ответ или вопрос был не отвечен.
    //Номер вопроса = номер символу в строке
    private fun coderCodeAnswer(charAnswer: Int) {
        var codeAnswerArray = codeAnswer

        when (charAnswer) {
            1 -> {
                codeAnswerArray = codeAnswerArray!!.replaceRange(
                    currentIndexThis..currentIndexThis,
                    "1"
                )
            }
            2 -> {
                codeAnswerArray = codeAnswerArray!!.replaceRange(
                    currentIndexThis..currentIndexThis,
                    "2"
                )
            }
            0 -> {
                codeAnswerArray = codeAnswerArray!!.replaceRange(
                    currentIndexThis..currentIndexThis,
                    "0"
                )
            }
        }
        codeAnswer = codeAnswerArray
    }

    val currentQuestionAnswer: Boolean
        get() = quizList[currentIndex].answer
    private val currentQuestionText: String
        get() = quizList[currentIndex].textResId

    private fun startTimer(typeAnswer: Boolean) {
        timer?.cancel()
        if (typeAnswer) {
            timer = object : CountDownTimer(
                getCurrentTimer(hardQuestion) * MILLIS_IN_SECONDS,
                MILLIS_IN_SECONDS
            ) {

                override fun onTick(millisUntilFinished: Long) {
                    _formattedTime.postValue(formatTime(millisUntilFinished))
                }

                override fun onFinish() {
                    _gameResult.postValue(false)
                }
            }
            timer?.start()
        }
    }

    fun getCurrentTimer(typeQuestion: Boolean): Int {
        return if (typeQuestion) TIME_HARD_QUESTION
        else TIME_LIGHT_QUESTION
    }

    fun startGame(time: Boolean) {
        startTimer(time)
    }

    private fun updatePercentAnswer(leftAnswer: Int, numAnswer: Int) {
        if (numAnswer == 0) _answerQuiz.value = 0
        else _answerQuiz.value = (100 * numAnswer / (leftAnswer + numAnswer))
    }

    fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private var _checkBlockLiveData = MutableLiveData<Boolean>()
    var checkBlockLiveData: LiveData<Boolean> = _checkBlockLiveData

    fun checkBlock() {
        _checkBlockLiveData.postValue(mapAnswer[currentIndex]!!)
    }

    private var _questionTextViewLiveData = MutableLiveData<String>()
    var questionTextViewLiveData: LiveData<String> = _questionTextViewLiveData
    private var _lastToastLiveData = MutableLiveData<String>()
    var lastToastLiveData: LiveData<String> = _lastToastLiveData

    fun updateQuestion() {
        log("updateQuestion")
        val questionTextResId = currentQuestionText

        if (updateAnswer) {
            _questionTextViewLiveData.postValue(questionTextResId)
        } else {
            _questionTextViewLiveData.postValue(questionTextResId)
            _lastToastLiveData.postValue(" ")
        }

        if (mapAnswer[currentIndex]!! && !mapAnswer[currentIndexThis]!!) checkTimer = false
        if (mapAnswer[currentIndex]!!) currentIndexThis = currentIndex

        loadTimer()
    }

    private var _loadTimerLiveData = MutableLiveData<Int>()
    var loadTimerLiveData: LiveData<Int> = _loadTimerLiveData

    fun loadTimer() {
        _loadTimerLiveData.postValue(s++)
    }

    private var _loadFrontListLiveData = MutableLiveData<Int>()
    var loadFrontListLiveData: LiveData<Int> = _loadFrontListLiveData
    private fun loadFrontList() {
        _loadFrontListLiveData.postValue(d++)
    }


    private var _loadResultTimerLiveData = MutableLiveData<Int>()
    var loadResultTimerLiveData: LiveData<Int> = _loadResultTimerLiveData

    fun loadResultTimer() {
        _loadResultTimerLiveData.postValue(f++)
    }

    private var _getQuizListLiveData = MutableLiveData<Int>()
    var getQuizListLiveData: LiveData<Int> = _getQuizListLiveData

    fun getQuizList() {
        _getQuizListLiveData.postValue(h++)
    }

    private fun log(text: String) {
        Log.d("QuestionListActivity", "$text")
    }

    private var _loadBDAnswerLiveData = MutableLiveData<Int>()
    var loadBDAnswerLiveData: LiveData<Int> = _loadBDAnswerLiveData
    fun loadPBAnswer() {
        log("loadPBAnswer")
        updatePercentAnswer(leftAnswer!!, constCurrentIndex)
        _loadBDAnswerLiveData.postValue(l++)
    }

    private var _loadToastLiveData = MutableLiveData<String>()
    var loadToastLiveData: LiveData<String> = _loadToastLiveData

    @InternalCoroutinesApi
    @SuppressLint("SetTextI18n")
    fun checkAnswer(userAnswer: Boolean) {
        log("checkAnswer")
        updatePercentAnswer(leftAnswer!!, numAnswer!!)

        val correctAnswer = currentQuestionAnswer
        when {
            isCheater -> {
                if (userAnswer == correctAnswer) {
                    points += 1
                    coderCodeAnswer(2)
                } else {
                    coderCodeAnswer(1)
                }

                useCheat()
                if (hardQuestion) {

                    _loadToastLiveData.postValue("Читер Х2")
                    showToast(R.string.nice)
                } else _loadToastLiveData.postValue("Читер! Бан!")
                showToast(R.string.judgment_toast)
            }
            userAnswer == correctAnswer -> {
                coderCodeAnswer(2)
                points += 1

                if (hardQuestion) {
                    _loadToastLiveData.postValue("HARD QUIZ!!")
                    showToast(R.string.nice)
                } else {
                    _loadToastLiveData.postValue("Верно!")
                    val toastCheckAnswer =
                        showToast(R.string.correct_toast)
                }
            }
            else -> {
                coderCodeAnswer(1)
                if (hardQuestion) {
                    _loadToastLiveData.postValue("HARD QUIZ!!")
                    showToast(R.string.nice)

                } else {
                    _loadToastLiveData.postValue("Не верно!")
                    showToast(R.string.incorrect_toast)
                }
            }
        }

        if (leftAnswer != 0) {
            setCrimeVar(getUpdateQuestion = true, insertCrime = false)
        }
    }

    private fun loadStars(points: Int): Int = points / 100

    private var _toastShowLiveData = MutableLiveData<Int>()
    var toastShowLiveData: LiveData<Int> = _toastShowLiveData

    private fun showToast(text: Int) {
        _toastShowLiveData.postValue(text)
    }

    fun loadUserName(question: String): String {
        log("loadUserName")
        var num = 0
        var name = ""
        listQuestion.forEach {
            if (it.listIdNameQuiz == question) {
                if (it.listPoints > num) {
                    num = it.listPoints
                    name = it.userName
                    loadStars(it.listPoints)
                }
            }
        }
        return name
    }

    fun loadStarsFun(question: String): Int {
        log("loadStarsFun")
        var num = 0
        var name = ""
        listQuestion.forEach {
            if (it.listIdNameQuiz == question) {
                if (it.listPoints > num) {
                    num = it.listPoints
                    name = it.userName
                    loadStars(it.listPoints)
                }
            }
        }
        return num
    }

    private var _viewResultLiveData = MutableLiveData<String>()
    var viewResultLiveData: LiveData<String> = _viewResultLiveData

    @SuppressLint("SetTextI18n")
    fun updatePersentView(leftAnswer: Int, persentPoints: Int) {
        log("updatePercentView")
        if (hardQuestion) _viewResultLiveData.postValue("(I don`t no) % Осталось - $leftAnswer")
        else _viewResultLiveData.postValue("$persentPoints % Осталось - $leftAnswer")
    }

    class QuizModelFactory(private var database: QuizDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
                @Suppress("UNCHECKED.CAST")
                return QuestionViewModel(database) as T
            }
            throw IllegalAccessException("Unknown ViewModelClass")
        }
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60

        private const val TIME_HARD_QUESTION = 10
        private const val TIME_LIGHT_QUESTION = 20
    }
}