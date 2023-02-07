package com.tpov.schoolquiz.presentation.question

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.*
import com.tpov.schoolquiz.R
import com.tpov.schoolquiz.data.database.entities.Question
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.data.database.entities.QuizDetail
import com.tpov.schoolquiz.data.model.ListQuestion
import com.tpov.schoolquiz.data.model.ListQuestionInfo
import com.tpov.schoolquiz.data.model.ListQuiz
import com.tpov.schoolquiz.domain.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@InternalCoroutinesApi
class QuestionViewModel @Inject constructor(
    private val insertInfoQuestionUseCase: InsertInfoQuestionUseCase,
    private val getInfoQuestionParamsUseCase: GetInfoQuestionParamsUseCase,
    private val getQuestionUseCase: GetQuestionUseCase,
    private val getInfoQuestionUseCase: GetInfoQuestionUseCase,
    private val getQuizUseCase: GetQuizUseCase,
    private val updateInfoQuestionUseCase: UpdateInfoQuestionUseCase,
    private val updateQuizUseCase: UpdateQuizUseCase,
    val getInfoQuestionListUseCase: GetInfoQuestionListUseCase,
    private val insertQuizUseCase: InsertQuizUseCase,
    private val insertQuestionUseCase: InsertQuestionUseCase,
    private val deleteQuizUseCase: DeleteQuizUseCase,
    private val getQuizListUseCase: GetQuizListUseCase
) : ViewModel() {

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

    private var listQuestion = mutableListOf<ListQuestion>()
    private lateinit var listQuestionInfo: List<ListQuestionInfo>
    private lateinit var listQuiz: List<ListQuiz>
    var quizList = mutableListOf<com.tpov.schoolquiz.data.model.Quiz>()
    var quizListHardQuestion = mutableListOf<com.tpov.schoolquiz.data.model.Quiz>()
    var quizListHQVar = mutableListOf<com.tpov.schoolquiz.data.model.Quiz>()

    var mapAnswer: MutableMap<Int, Boolean> = mutableMapOf()
    val TAG = "QuestionViewModel"


    private lateinit var list1: List<Question>
    private lateinit var list2: List<QuizDetail>
    private lateinit var list3: List<Quiz>

    //Начальные данные для квеста
    var numQuestion: Int? = 0               //К-во вопросов
    var numAnswer: Int? = 0                 //К-во ответов
    var leftAnswer: Int? = 0                //WTF
    var codeAnswer: String? =
        ""            //Строка показывает как игрок ответил на все вопросы (напр. "02102" - Если в квесте 5 вопросов)
    var codeMap: String? =
        ""               //Строка показывает какой вопрос был отвечен что-бы блокировать кнопки (напр "01011" - если 5 вопросов)
    var currentIndexThis: Int = -1          //Номер вопроса на который стоит таймер
    var isCheater: Boolean = false          //Использовал ли игрок жизнь
    var updateAnswer: Boolean =
        true        //Нужно ли восстанавливать эту сессию при следующем запуске
    var insertQuiz: Boolean =
        true          //Заглушка от повторного запуска обсервера getInfoQuestion
    var loadedQuestion: Boolean = true      //Заглушка от повторного запуска
    var constCurrentIndex: Int = 0          //Сколько вопросов осталось
    var points: Int = 0
    var percentPoints: Int = 0
    var cheatPoints: Int = 3
    var charMap: String? = ""               //Нужно для codeMap
    var idQuiz = 1                          //id в бд
    var userName: String? = ""
    var idUser = ""                         //Имя квеста который хочет пройти пользователь
    var hardQuestion = false
    var updateQuiz = 0
    var stars = 0
    var percentAnswer = 0
    var currentIndex = 1                    //Номер вопроса который видет пользователь
    var checkTimer = false

    //Ильтераторы с помощю которых при их изменении активируется ливдата
    var x = 0
    var i: Int = 0
    var j: Int = 0
    private var z = 0
    private var s = 0
    private var d = 0
    private var f = 0
    private var h = 0
    private var l = 0

    fun insertQuizDetail(quiz: Quiz) = viewModelScope.launch { insertQuizUseCase(quiz) }
    fun insertQuestion(question: Question) =
        viewModelScope.launch { insertQuestionUseCase(question) }

    fun deleteQuiz(id: Int, deleteQuestion: Boolean, nameQuiz: String) = viewModelScope.launch {
        deleteQuizUseCase(id, deleteQuestion, nameQuiz)
    }

    private fun insertQuestion(updateAnswer: Boolean, insertQuiz: QuizDetail, idUser: String) =
        viewModelScope.launch {
            Log.d("v2.4", "insertQuestion")
            insertInfoQuestionUseCase(updateAnswer, insertQuiz, idUser)
        }

    private fun getInfoQuestion(updateQuestion: Boolean, insertQuiz: QuizDetail, idUser: String) =
        viewModelScope.launch {
            Log.d("v2.4", "getInfoQuestion")
            getInfoQuestionParamsUseCase(updateQuestion, insertQuiz, idUser)
        }

    private var _viewResultLiveData = MutableLiveData<String>()
    var viewResultLiveData: LiveData<String> = _viewResultLiveData

    private var _cheatPointsLifeLiveData = MutableLiveData<String>()
    var cheatPointsLiveData: LiveData<String> = _cheatPointsLifeLiveData

    private var _loadToastLiveData = MutableLiveData<String>()
    var loadToastLiveData: LiveData<String> = _loadToastLiveData

    private var _loadTimerLiveData = MutableLiveData<Int>()
    var loadTimerLiveData: LiveData<Int> = _loadTimerLiveData

    private var _questionTextViewLiveData = MutableLiveData<String>()
    var questionTextViewLiveData: LiveData<String> = _questionTextViewLiveData

    private var _lastToastLiveData = MutableLiveData<String>()
    var lastToastLiveData: LiveData<String> = _lastToastLiveData

    private var _checkBlockLiveData = MutableLiveData<Boolean>()
    var checkBlockLiveData: LiveData<Boolean> = _checkBlockLiveData

    private var _cheatButtonLiveData = MutableLiveData<Boolean>()
    var cheatButtonLiveData: LiveData<Boolean> = _cheatButtonLiveData

    private var _springAnim = MutableLiveData<Boolean>()
    var springAnim: LiveData<Boolean> = _springAnim

    private var _moveToPrevLiveData = MutableLiveData<Int>()
    var moveToPrevLiveData: LiveData<Int> = _moveToPrevLiveData

    private var _moveToNextLiveData = MutableLiveData<Int>()
    var moveToNextLiveData: LiveData<Int> = _moveToNextLiveData

    private var _loadResultTimerLiveData = MutableLiveData<Int>()
    var loadResultTimerLiveData: LiveData<Int> = _loadResultTimerLiveData

    private var _getQuizListLiveData = MutableLiveData<Int>()
    var getQuizListLiveData: LiveData<Int> = _getQuizListLiveData

    private var _loadFrontListLiveData = MutableLiveData<Int>()
    var loadFrontListLiveData: LiveData<Int> = _loadFrontListLiveData

    private var _loadBDAnswerLiveData = MutableLiveData<Int>()
    var loadBDAnswerLiveData: LiveData<Int> = _loadBDAnswerLiveData

    private var _toastShowLiveData = MutableLiveData<Int>()
    var toastShowLiveData: LiveData<Int> = _toastShowLiveData

    private var _getInfoQuestionLiveData = MutableLiveData<List<QuizDetail>>()
    var getInfoQuestionLiveData: LiveData<List<QuizDetail>> = _getInfoQuestionLiveData

    var getQuestion: LiveData<List<Question>> = getQuestionUseCase()
    var getInfoQuestion: LiveData<List<QuizDetail>> = getInfoQuestionUseCase()
    lateinit var getInfoQuestionList: List<QuizDetail>
    lateinit var getQuizList: List<Quiz>
    var getQuiz: LiveData<List<Quiz>> = getQuizUseCase()

    fun inits() {
        getInfoQuestionList = getInfoQuestionListUseCase()
        Log.d("startAdd", "${getInfoQuestionList}")

        Log.d("startAdd", "${idUser}")

        Log.d("v2.4", "result")

    }

    fun insertQuizDetail() {

        Log.d("startAdd", "$idUser")
        insertQuestion(true, insertQuizDetail(idUser), idUser)
        getInfoQuestion(true, insertQuizDetail(idUser), idUser)
    }

    fun updateInfoQuestion(quizDetail: QuizDetail) = viewModelScope.launch {
        updateInfoQuestionUseCase(quizDetail)
    }

    fun updateQuiz(quiz: Quiz) = viewModelScope.launch {
        Log.d("v2.4", "updateQuiz ${quiz.toString()}")
        if (quiz.stars != 0) updateQuizUseCase(quiz)
    }

    fun getUpdateQuiz(idUser: String) {
        _getInfoQuestionLiveData.postValue(getInfoQuestionList)

    }

    fun getInfoQuestion() {

        getInfoQuestionLiveData.observeForever {
            if (insertQuiz) {
                !insertQuiz
                it.forEach { item ->
                    if (item.updateAnswer) loadCrime(item)
                }
            }
        }
    }

    private fun useCheat() {
        cheatPoints--
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
                setQuizVar(true, false)
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

            Log.d("v2.4", "constCurrentIndex: $constCurrentIndex")
            Log.d("v2.4", "numAnswer: $numAnswer")
            if (constCurrentIndex == numAnswer) {
                result(points)
            } else {
                setQuizVar(true, false)
            }
        }
        if (constCurrentIndex != numAnswer) {
            updatePersentView(leftAnswer!!, percentPoints)
        }
    }

    fun falseButton() {
        //Проверка нужно ли показывать следующий вопрос после нажатия на кнопку
        if (!updateAnswer) {
            checkBlockMap()
            checkBlock()
            coderBlockMap()
            checkAnswer(false)
            constCurrentIndex++
            resultTextView(points)
            if (constCurrentIndex == numAnswer) {
                result(points)
            } else {
                setQuizVar(true, false)
            }
        } else {
            checkBlockMap()
            checkBlock()
            coderBlockMap()
            checkAnswer(false)
            constCurrentIndex++
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
            setQuizVar(getUpdateQuestion = true, insertCrime = false)
        }
        if (constCurrentIndex != numAnswer) {
            updatePersentView(leftAnswer!!, percentPoints)
        }
    }

    private fun springAnim(b: Boolean) {
        _springAnim.postValue(b)
    }


    fun endTimer() {
        if (!updateAnswer) {
            checkBlockMap()
            checkBlock()
            coderBlockMap()
            checkAnswer(intToBool(Random.nextInt(0, 2)))
            constCurrentIndex++
            resultTextView(points)
            if (constCurrentIndex == numAnswer) {
                result(points)
            } else {
                setQuizVar(true, false)
            }
        } else {
            checkBlockMap()
            checkBlock()
            coderBlockMap()
            checkAnswer(intToBool(Random.nextInt(0, 2)))

            constCurrentIndex++
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
            setQuizVar(true, false)
        }
        if (constCurrentIndex != numAnswer) {
            updatePersentView(leftAnswer!!, percentPoints)
        }
        checkTimer = false
    }

    private fun moveToNext() {
        _moveToNextLiveData.postValue(z++)
    }

    private fun intToBool(nextInt: Int): Boolean = nextInt == 1

    fun getHardQuestion(stars: Int) = stars >= MAX_PERCENT

    //Когда отвечаете на вопрос квеста, все ответы сохраняются в виде одной строки с набором символов 0,1,2
    //где 0 - не отвеченный вопрос, 1 - не верный ответ, 2 - верный.
    //Это позволяет восстановить сессию, подсчитывать результаты, показывать список вопросов с результатами
    private fun createCodeAnswer() {
        codeAnswer = ""
        repeat(quizList.size) {
            codeAnswer += '0'
        }
    }


    private fun result(points: Int) {
        percentPoints =
            if (hardQuestion) (points * COEF_PERCENT_HARD_QUIZ / numQuestion!!) + MAX_PERCENT
            else points * MAX_PERCENT / numQuestion!!
        showToast(percentPoints)
        updatePersentView(leftAnswer!!, percentPoints)
        setQuizVar(getUpdateQuestion = false, insertCrime = false)

        loadFrontList()
        checkTimer = false
        loadTimer()

        Log.d("v2.4", "result")
        getQuestion.observeForever {

            Log.d("v2.4", "result list1 $it")
            list1 = it
        }
        list2 = getInfoQuestionListUseCase()
        list3 = getQuizListUseCase()

        loadQuiz(list2, list1, list3)
    }

    //false в маппере значит, что мы ответили на этот вопрос
    private fun checkBlockMap() {
        mapAnswer[currentIndexThis] = false
        leftAnswer = leftAnswer!!.minus(1)
        updatePersentView(leftAnswer!!, percentPoints)
        coderBlockMap()
    }

    //Переобразовывам данные маппера в строку для сохранения в бд
    private fun coderBlockMap() {
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
        percentPoints = points * MAX_PERCENT / numAnswer!!
        updatePersentView(leftAnswer!!, percentPoints)

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
        else _answerQuiz.value = (MAX_PERCENT * numAnswer / (leftAnswer + numAnswer))
    }

    fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    fun checkBlock() {
        _checkBlockLiveData.postValue(mapAnswer[currentIndex]!!)
    }

    fun updateQuestion() {
        log("updateQuestion")
        val questionTextResId = currentQuestionText

        if (updateAnswer) {
            _questionTextViewLiveData.postValue(questionTextResId)
        } else {
            _questionTextViewLiveData.postValue(questionTextResId)
            _lastToastLiveData.postValue(" ")
        }

        Log.d(
            "testAdd",
            "$currentIndex, $currentIndexThis, ${mapAnswer[currentIndex]}, ${mapAnswer[currentIndexThis]}"
        )
        Log.d("testAdd", "${getInfoQuestionListUseCase()}")
        //if (mapAnswer[currentIndex]!! && !mapAnswer[currentIndexThis]!!) checkTimer = false
        //if (mapAnswer[currentIndex]!!) currentIndexThis = currentIndex

        loadTimer()
    }

    fun loadTimer() {
        _loadTimerLiveData.postValue(s++)
    }

    private fun loadFrontList() {
        _loadFrontListLiveData.postValue(d++)
        Log.d("testObserver", "$d")
    }


    fun loadResultTimer() {
        _loadResultTimerLiveData.postValue(f++)
    }

    private fun log(text: String) {
        Log.d("QuestionListActivity", "$text")
    }

    private fun loadPBAnswer() {
        log("loadPBAnswer")
        updatePercentAnswer(leftAnswer!!, constCurrentIndex)
        _loadBDAnswerLiveData.postValue(l++)
    }


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
            setQuizVar(getUpdateQuestion = true, insertCrime = false)
        }
    }

    private fun loadStars(points: Int): Int = points / MAX_PERCENT

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

    @SuppressLint("SetTextI18n")
    fun updatePersentView(leftAnswer: Int, persentPoints: Int) {
        log("updatePercentView")
        if (hardQuestion) _viewResultLiveData.postValue("(I don`t no) % Осталось - $leftAnswer")
        else _viewResultLiveData.postValue("$persentPoints % Осталось - $leftAnswer")
    }


    fun getQuizLists(it: List<Question>) {
        list1 = it
        quizList.clear()

        loadedQuestion = false
        //Загружаем все легкие и сложные вопросы в списки.
        it.forEach { item ->

            Log.d("testAdd", "forEach")
            if (item.idListNameQuestion == idUser) {
                if (item.typeQuestion) quizListHardQuestion.add(
                    com.tpov.schoolquiz.data.model.Quiz(
                        item.nameQuestion,
                        item.answerQuestion
                    )
                )
                else quizList.add(
                    com.tpov.schoolquiz.data.model.Quiz(
                        item.nameQuestion,
                        item.answerQuestion
                    )
                )
            }
        }

        //В зависимости сложные или легкие вопросы нужно отобразить, мы выбераем из двух списков - один нужный список
        if (hardQuestion) {
            quizListHQVar = quizList
            quizList = quizListHardQuestion
        } else {
            quizListHQVar = quizList
        }

        //Если это новая сессия, то в параметрах квеста(entity QuestionInfo) не будут некоторые начальные значения, создаем их сами.

        Log.d("testAdd", "numQuestion = $numQuestion")
        if (numQuestion == null || numQuestion == 0) {
            numQuestion = quizList.size
            numAnswer = quizList.size
            leftAnswer = quizList.size
            charMap = ""
            createCodeAnswer()
            coderBlockMap()
            Log.d("asdawda", "$numAnswer")
        }
        updatePersentView(leftAnswer!!, percentPoints)
        decoderBlockMap()

        loadedQuestion = false
        setQuizVar(true, false)

        Log.d("dsawads", "$numAnswer")
        checkBlock()
        updateQuestion()
        loadPBAnswer()
        loadResultTimer()
    }


    //Подсчитываем результаты прохождения квеста и заполняем их в бд
    private fun loadQuiz(
        quizDelailDB: List<QuizDetail>,
        questionDB: List<Question>,
        quizDB: List<Quiz>
    ) {
        Log.d("testObserver", "1")
        //Загружаем все данные из таблицы QuizDetail
        var i = 0
        var j = 0
        var k = true

        quizDelailDB.forEach {
            if (it.charMap != null) listQuestion.add(listQuestion(it))
            Log.d("v2.4", "quizDelailDB: $it")
        }

        //Обновляем данные квеста
        quizDB.forEach {
            if (it.nameQuestion == idUser) {

                if (k) {
                    k = false

                    Log.d("v2.4", "updateQuiz: $it")
                    updateQuiz(
                        Quiz(
                            it.id,
                            it.nameQuestion,
                            it.userName,
                            it.data,
                            getStars(listQuestion),
                            it.numQ,
                            it.numA,
                            it.numHQ,
                            getStartAll(listQuestion)
                        )
                    )
                }
            }
        }
    }

    private fun getStartAll(listQuestion: List<ListQuestion>): Int {
        var count = 0
        var stars = 0
        listQuestion.forEach {
            count++
            stars += it.listPoints
        }
        return stars / count
    }

    private fun getStars(listQuestion: List<ListQuestion>): Int {
        var maxStars = 0

        Log.d("v2.4", "getStars: $listQuestion")
        listQuestion.forEach {
            if (maxStars < it.listPoints) maxStars = it.listPoints
        }
        return maxStars
    }


    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60

        private const val TIME_HARD_QUESTION = 10
        private const val TIME_LIGHT_QUESTION = 20

        private const val MAX_PERCENT = 100
        private const val COEF_PERCENT_HARD_QUIZ =
            20 //Это нужно что-бы посчитать проценты сложных вопросов
    }
}