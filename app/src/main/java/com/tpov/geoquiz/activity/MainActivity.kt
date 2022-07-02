package com.tpov.geoquiz.activity

/**
 * Этот код писался с самого начала моего обучения в андроид студио, как он работает - никто не знает :)
 */
import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.lifecycle.ViewModelProvider
import com.tpov.geoquiz.*
import com.tpov.geoquiz.Services.MyService
import com.tpov.geoquiz.database.MainViewModel
import com.tpov.geoquiz.entities.Crime
import com.tpov.geoquiz.entities.FrontList
import com.tpov.shoppinglist.utils.TimeManager
import kotlinx.coroutines.InternalCoroutinesApi
import kotlin.random.Random

private const val REQUEST_CODE_CHEAT = 0
private const val UPDATE_CURRENT_INDEX = 1

@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val viewModelFactory by lazy {
        MainViewModelFactory(hardQuestion, persentAnswer, application)
    }

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FrontViewModel::class.java]
    }

    private var listCrime = mutableListOf<ListCrime>()
    private var listCrimeNewQuiz = mutableListOf<ListCrimeNewQuiz>()
    private var listFrontList = mutableListOf<ListFrontList>()

    var quizList = mutableListOf<Quiz>()
    var quizListHardQuestion = mutableListOf<Quiz>()
    var quizListHQVar = mutableListOf<Quiz>()

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var prefButton: Button
    private lateinit var cheatButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var updateAnswerButton: Button
    private lateinit var viewResult: TextView
    private lateinit var cheatPointsLife: TextView
    private lateinit var lastToast: TextView
    private lateinit var vAndroid: TextView
    private lateinit var listQuestionButton: Button
    private lateinit var tvTimer: TextView
    private lateinit var pbAnswer: ProgressBar
    private lateinit var tv321: TextView
    private lateinit var viewBackground: ConstraintLayout

    private var numQ: Int? = 0
    private var numQuestion: Int? = 0
    private var numAnswer: Int? = 0
    private var leftAnswer: Int? = 0

    private var idGeoQuiz: String? = NAME_GEOQUIZ
    private var codeAnswer: String? = ""
    private var codeMap: String? = ""
    private var currentIndexThis: Int = -1
    private var isCheater: Boolean = false
    private var updateAnswer: Boolean = true
    private var insertCrime: Boolean = true
    private var insertCrimeNewQuiz: Boolean = true
    private var constCurrentIndex: Int = 0
    private var points: Int = 0
    private var persentPoints: Int = 0
    private var cheatPoints: Int = 3
    private var charMap: String? = ""
    private var i: Int = 0
    private var j: Int = 0
    private var idCrime = 0
    private var userName: String? = ""
    private var idUser = ""
    private var closeActivity = true
    private var hardQuestion = false
    private var updateFrontList = 0
    private var stars = 0
    private var quizA = 0
    private var persentAnswer = 0
    private var currentIndex = 0
    private var thisTimer = false
    private var checkTimer = false

    private var mapAnswer: MutableMap<Int, Boolean> = mutableMapOf(
        0 to true,
        1 to true,
        2 to true,
        3 to true,
        4 to true,
        5 to true
    )
    private val TAG = "QuizViewModel"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val nameQuestionUser = intent.getStringExtra(NAME_QUESTION)
        userName = intent.getStringExtra(NAME_USER)
        stars = intent.getStringExtra(STARS)!!.toInt()
        idUser = nameQuestionUser!!
        testMainActivity(0)
        hardQuestion = getHardQuestion(stars)


        getUpdateCrime(true, idUser)
        getQuizList()

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prefButton = findViewById(R.id.pref_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)
        viewResult = findViewById(R.id.viewResult)
        cheatPointsLife = findViewById(R.id.cheatPointsLife)
        lastToast = findViewById(R.id.lastToast)
        vAndroid = findViewById(R.id.vAndroid)
        vAndroid.text =
            "vAndroid - ${Build.VERSION.SDK_INT}, vCode - ${Build.VERSION_CODES.M}"
        tvTimer = findViewById(R.id.tvTimer)
        pbAnswer = findViewById(R.id.pbAnswer)
        tv321 = findViewById(R.id.tv_3_2_1)
        viewBackground = findViewById(R.id.view_background)

        if (hardQuestion) {
            listQuestionButton.isEnabled = false
            listQuestionButton.isClickable = false

            viewBackground.setBackgroundResource(R.color.background_hard_question)
        }
        trueButton.setOnClickListener {

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
                    val toastNull = Toast.makeText(this, R.string.null_toast, Toast.LENGTH_SHORT)
                    toastNull.show()
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
        falseButton.setOnClickListener { _: View ->

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
                    val toastNull = Toast.makeText(this, R.string.null_toast, Toast.LENGTH_SHORT)
                    toastNull.show()
                    springAnim(true)
                } else {
                    moveToNext()
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
        }

        cheatButton.setOnClickListener { view ->
            val answerIsTrue = currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val options =
                    ActivityOptions.makeClipRevealAnimation(view, 0, 0, view.width, view.height)
                startActivityForResult(intent, REQUEST_CODE_CHEAT, options.toBundle())
            } else {
                startActivityForResult(intent, REQUEST_CODE_CHEAT)
            }
        }

        nextButton.setOnClickListener {
            if (currentIndex == numAnswer!! - 1) {
                val toastNull = Toast.makeText(this, R.string.null_toast, Toast.LENGTH_SHORT)
                toastNull.show()
                springAnim(true)
            } else {
                moveToNext()
            }
            checkBlock()

        }
        prefButton.setOnClickListener {
            if (currentIndex == 0) {
                Toast.makeText(this, R.string.null_toast, Toast.LENGTH_SHORT).show()
                springAnim(false)
            } else {
                moveToPref()
            }
            checkBlock()
        }

        actionBarSettings()
        startService(Intent(this, MyService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()

        stopService(Intent(this, MyService::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return true
    }

    override fun getMenuInflater(): MenuInflater {
        
        return super.getMenuInflater()
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_auto_update_answer -> {
                Log.d("MainActivity", "itemAutoUpdateAnswer")
                item.isChecked = !item.isChecked
                updateAnswer = if (item.isChecked) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        item.tooltipText = "true"
                    }
                    true
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        item.tooltipText = "false"
                    }
                    false
                }
            }

            R.id.item_list_answer -> {
                Log.d("MainActivity", "itemListanswer")
                val questionActivityIntent = Intent(this, QuestionActivity::class.java)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val optionsList =
                        ActivityOptions.makeClipRevealAnimation(
                            View(this),
                            0,
                            0,
                            View(this).width,
                            View(this).height
                        )

                    questionActivityIntent.putExtra(EXTRA_CURRENT_INDEX, currentIndex)   //Output
                    questionActivityIntent.putExtra(EXTRA_CODE_ANSWER, codeAnswer)
                    questionActivityIntent.putExtra(EXTRA_CODE_ID_USER, idUser)
                    startActivityForResult(questionActivityIntent, UPDATE_CURRENT_INDEX)
                } else {
                    questionActivityIntent.putExtra(
                        EXTRA_UPDATE_CURRENT_INDEX,
                        currentIndex
                    )   //Output
                    startActivityForResult(questionActivityIntent, UPDATE_CURRENT_INDEX)
                }
            }
        }
        return true
    }

    private fun endTimer() {

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
                val toastNull = Toast.makeText(this, R.string.null_toast, Toast.LENGTH_SHORT)
                toastNull.show()
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

    private fun intToBool(nextInt: Int): Boolean = nextInt == 1

    private fun getHardQuestion(stars: Int): Boolean {
        return stars >= 100
    }

    private fun testMainActivity(numI: Int) {

        Log.d("MainActivity", "$numI")
        Log.d("MainActivity", "numQuestion = $numQuestion")
        Log.d("MainActivity", "numAnswer = $numAnswer")
        Log.d("MainActivity", "leftAnswer = $leftAnswer")
        Log.d("MainActivity", "codeAnswer = $codeAnswer")
        Log.d("MainActivity", "codeMap = $codeMap")
        Log.d("MainActivity", "currentIndex = $currentIndexThis")
        Log.d("MainActivity", "isCheater = $isCheater")
        Log.d("MainActivity", "updateAnswer = $updateAnswer")
        Log.d("MainActivity", "insertCrime = $insertCrime")
        Log.d("MainActivity", "insertCrimeNewQuiz = $insertCrimeNewQuiz")
        Log.d("MainActivity", "constCurrentIndex = $constCurrentIndex")
        Log.d("MainActivity", "points = $points")
        Log.d("MainActivity", "persentPoints = $persentPoints")
        Log.d("MainActivity", "cheatPoints = $cheatPoints")
        Log.d("MainActivity", "charMap = $charMap")
        Log.d("MainActivity", "i = $i")
        Log.d("MainActivity", "j = $j")
        Log.d("MainActivity", "idCrime = $idCrime")
        Log.d("MainActivity", "userName = $userName")
        Log.d("MainActivity", "idUser = $idUser")
        Log.d("MainActivity", "____________________________________________")

    }

    private fun getUpdateCrime(updateQuiz: Boolean, idUser: String) {
        mainViewModel.insertAnswerCrime(true, insertQuiz(idUser), idUser)
        mainViewModel.updateUnswerMutableCrime.observe(this, {

            if (insertCrime) {
                !insertCrime
                it.forEach { item ->
                    // if (item.idNameQuiz != idUser) recreate()
                    loadCrime(item)
                    testMainActivity(2)
                }
            }
        })

        mainViewModel.getUpdateAnswerCrime(true, insertQuiz(idUser), idUser)
    }

    fun springAnim(next: Boolean) {
        var START_VELOCITY = if (next) -5000f
        else 5000f

        var springAnimation: SpringAnimation = SpringAnimation(questionTextView, DynamicAnimation.X)
        var springForce: SpringForce = SpringForce()
        springForce.finalPosition = questionTextView.x
        springForce.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        springForce.stiffness = SpringForce.STIFFNESS_HIGH

        springAnimation.spring = springForce
        springAnimation.setStartVelocity(START_VELOCITY)
        springAnimation.start()
    }

    fun insertQuiz(idName: String): Crime {
        return Crime(
            null,
            idName,
            userName,
            TimeManager.getCurrentTime(),
            null,
            null,
            0,
            false,
            0,
            0,
            0,
            3,
            null,
            0,
            0,
            true,
            null,
            null,
            null
        )
    }


    private fun loadCrime(quizTable: Crime) {
        codeAnswer = quizTable.codeAnswer
        codeMap = quizTable.codeMap
        currentIndexThis = quizTable.currentIndex
        isCheater = quizTable.isCheater
        updateAnswer = quizTable.updateAnswer
        constCurrentIndex = quizTable.constCurrentIndex
        points = quizTable.points
        persentPoints = quizTable.persentPoints
        cheatPoints = quizTable.cheatPoints
        charMap = quizTable.charMap
        i = quizTable.i
        j = quizTable.j
        idCrime = quizTable.id!!

        leftAnswer = quizTable.leftUnswer
        numQuestion = quizTable.numQuestion
        numAnswer = quizTable.numAnswer
        currentIndex = currentIndexThis

        if (userName == "") {
            userName = quizTable.userName
        }
        testMainActivity(1)

        mainViewModel.getQuestionCrimeNewQuiz()

    }

    private fun actionBarSettings() {       //Кнопка назад в баре
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun getQuizList() {

        mainViewModel.allCrimeNewQuiz.observe(this, {
            if (insertCrimeNewQuiz) {
                insertCrimeNewQuiz = false
                quizList.clear()
                it.forEach { item ->
                    if (item.idListNameQuestion == idUser) {
                        if (item.typeQuestion) quizListHardQuestion.add(
                            Quiz(
                                item.nameQuestion,
                                item.answerQuestion
                            )
                        )
                        else quizList.add(Quiz(item.nameQuestion, item.answerQuestion))
                    }
                }
                testMainActivity(3)

                //if (quizListHardQuestion.isEmpty()) quizListHardQuestion = quizList
                if (hardQuestion) {
                    quizListHQVar = quizList
                    quizList = quizListHardQuestion
                } else {
                    quizListHQVar = quizList
                }
                Log.d("MainActivity", "quizList = ${quizListHardQuestion}")
                Log.d("MainActivity", "quizList = ${quizListHQVar}")

                if (numQuestion == null) {

                    numQuestion = quizList.size
                    numAnswer = quizList.size
                    leftAnswer = quizList.size
                    charMap = ""
                    createCodeUnswer()
                    coderBlockMap()
                }
                updatePersentView(leftAnswer!!, persentPoints)
                decoderBlockMap()
                checkBlock()
                insertCrimeNewQuiz = false
                setCrimeVar(true, false)

                updateQuestion()
                loadPBAnswer(persentAnswer)

                loadResultTimer()
            }
        })
    }

    private fun loadTimer(hardQuestion: Boolean) {
        if (!checkTimer) {
            viewModel.startGame(mapAnswer[currentIndex]!!)
            viewModel.formattedTime.observe(this, {
                tvTimer.text = it
                if (it[3] == '0' && it[4] == '3') anim321(3)
                if (it[3] == '0' && it[4] == '2') anim321(2)
                if (it[3] == '0' && it[4] == '1') anim321(1)
            })
            checkTimer = true
        }
    }

    @SuppressLint("ResourceType")
    private fun anim321(num: Int) {
        tv321.text = num.toString()

        var anim = AnimationUtils.loadAnimation(this@MainActivity, R.anim.time_3_2_1)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                tv321.visibility = View.VISIBLE

            }

            override fun onAnimationEnd(p0: Animation?) {
                tv321.visibility = View.GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })
        tv321.startAnimation(anim)
    }

    private fun loadPBAnswer(persentPoints: Int) {
        viewModel.updatePercentAnswer(leftAnswer!!, constCurrentIndex)
        viewModel.answerQuiz.observe(this, {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                pbAnswer.setProgress(it, true)
            } else {
                pbAnswer.progress = it
            }
        })
    }

    private fun loadResultTimer() {
        viewModel.gameResult.observe(this, {
            if (!it) endTimer()
        })
    }

    private fun setCrimeVar(getUpdateQuestion: Boolean, insertCrime: Boolean) {

        if (!insertCrime) {
            if (hardQuestion) {
                var crimeUpdate = Crime(
                    idCrime,
                    idUser,
                    userName,
                    TimeManager.getCurrentTime(),
                    codeAnswer,
                    codeMap,
                    currentIndexThis,
                    isCheater,
                    constCurrentIndex,
                    points,
                    persentPoints,
                    cheatPoints,
                    charMap,
                    i,
                    j,
                    false,
                    leftAnswer,
                    numQuestion,
                    numAnswer
                )
                mainViewModel.updateCrime(crimeUpdate)
            } else {
                var crimeUpdate = Crime(
                    idCrime,
                    idUser,
                    userName,
                    TimeManager.getCurrentTime(),
                    codeAnswer,
                    codeMap,
                    currentIndexThis,
                    isCheater,
                    constCurrentIndex,
                    points,
                    persentPoints,
                    cheatPoints,
                    charMap,
                    i,
                    j,
                    getUpdateQuestion,
                    leftAnswer,
                    numQuestion,
                    numAnswer
                )
                mainViewModel.updateCrime(crimeUpdate)
            }
            testMainActivity(10)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            isCheater = data.getBooleanExtra(EXTRA_ANSWER_SHOW, false)
        } else if (requestCode == UPDATE_CURRENT_INDEX) {
            currentIndex = data.getIntExtra(EXTRA_UPDATE_CURRENT_INDEX, 0)

        }
        updateQuestion()
        checkBlock()
        loadTimer(false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString("codeMap", codeMap)
            putString("codeAnswer", codeAnswer)
            putInt("currentIndex", currentIndexThis)
            putInt("constCurrentIndex", constCurrentIndex)
            putInt("points", points)
            putInt("persentPoints", persentPoints)
            putBoolean("isCheater", isCheater)
            putInt("cheatPoints", cheatPoints)
            putInt("leftAnswer", leftAnswer!!)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(saveInstanceState: Bundle) {
        super.onRestoreInstanceState(saveInstanceState)
        codeMap = saveInstanceState.getString("codeMap")!!
        codeAnswer = saveInstanceState.getString("codeAnswer")!!
        currentIndexThis = saveInstanceState.getInt("currentIndex")
        points = saveInstanceState.getInt("points")
        persentPoints = saveInstanceState.getInt("persentPoints")
        isCheater = saveInstanceState.getBoolean("isCheater")
        cheatPoints = saveInstanceState.getInt("cheatPoints")
        leftAnswer = saveInstanceState.getInt("leftAnswer")

        vAndroid.text =
            "vAndroid - ${android.os.Build.VERSION.SDK_INT}, vCode - ${android.os.Build.VERSION_CODES.M}"
        updatePersentView(leftAnswer!!, persentPoints)
        decoderBlockMap()
        checkBlock()
    }

    private fun updateQuestion() {
        val questionTextResId = currentQuestionText

        if (updateAnswer) {
            questionTextView.text = questionTextResId
        } else {
            questionTextView.text = questionTextResId
            lastToast.text = " "
        }

        if (mapAnswer[currentIndex]!! && !mapAnswer[currentIndexThis]!!) checkTimer = false
        if (mapAnswer[currentIndex]!!) currentIndexThis = currentIndex

        loadTimer(false)
    }

    private fun moveToPref() {

        var animPref1 = AnimationUtils.loadAnimation(this, R.anim.pref_question1)
        var animPref2 = AnimationUtils.loadAnimation(this, R.anim.pref_question2)

        animPref1.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                questionTextView.visibility = View.GONE
                currentIndex = (currentIndex - 1) % numQuestion!!
                updateQuestion()

                questionTextView.startAnimation(animPref2)
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })

        animPref2.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                questionTextView.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(p0: Animation?) {
                checkBlock()
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })
        questionTextView.startAnimation(animPref1)
    }


    private fun moveToNext() {
        var animNext1 = AnimationUtils.loadAnimation(this, R.anim.next_question1)
        var animNext2 = AnimationUtils.loadAnimation(this, R.anim.next_question2)

        animNext1.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                questionTextView.visibility = View.GONE
                currentIndex = (currentIndex + 1) % numQuestion!!
                updateQuestion()

                questionTextView.startAnimation(animNext2)
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })
        animNext2.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

                questionTextView.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(p0: Animation?) {
                checkBlock()
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })
        questionTextView.startAnimation(animNext1)

    }

    private val currentQuestionAnswer: Boolean
        get() = quizList[currentIndex].answer
    private val currentQuestionText: String
        get() = quizList[currentIndex].textResId

    @SuppressLint("SetTextI18n")
    private fun checkAnswer(userAnswer: Boolean) {
        viewModel.updatePercentAnswer(leftAnswer!!, numAnswer!!)

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
                    lastToast.text = "Читер Х2"
                    val toastCheckAnswer = Toast.makeText(this, R.string.nice, Toast.LENGTH_SHORT)
                    toastCheckAnswer.show()
                } else lastToast.text = "Читер! Бан!"
                val toastCheckAnswer =
                    Toast.makeText(this, R.string.judgment_toast, Toast.LENGTH_SHORT)
                toastCheckAnswer.show()

            }
            userAnswer == correctAnswer -> {
                coderCodeAnswer(2)
                points += 1

                if (hardQuestion) {
                    lastToast.text = "HARD QUIZ!!"
                    val toastCheckAnswer = Toast.makeText(this, R.string.nice, Toast.LENGTH_SHORT)
                    toastCheckAnswer.show()
                } else {
                    lastToast.text = "Верно!"
                    val toastCheckAnswer =
                        Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT)
                    toastCheckAnswer.show()
                }
            }
            else -> {
                coderCodeAnswer(1)
                if (hardQuestion) {
                    lastToast.text = "HARD QUIZ!!"
                    val toastCheckAnswer = Toast.makeText(this, R.string.nice, Toast.LENGTH_SHORT)
                    toastCheckAnswer.show()

                } else {
                    lastToast.text = "Не верно!"
                    val toastCheckAnswer =
                        Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT)
                    toastCheckAnswer.show()
                }
            }
        }

        if (leftAnswer != 0) {
            setCrimeVar(true, false)
        }
    }

    private fun createCodeUnswer() {
        codeAnswer = ""
        quizList.forEach {
            codeAnswer += '0'
        }
    }

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

    private fun result(points: Int) {
        persentPoints = if (hardQuestion) (points * 20 / numQuestion!!) + 100
        else points * 100 / numQuestion!!
        val toastPoints =
            Toast.makeText(this, "$persentPoints %", Toast.LENGTH_SHORT)
        toastPoints.show()
        updatePersentView(leftAnswer!!, persentPoints)
        setCrimeVar(getUpdateQuestion = false, insertCrime = false)

        loadFrontList()
        checkTimer = false
        loadTimer(false)
        testMainActivity(11)
    }

    private fun checkBlock() {
        if (mapAnswer[currentIndex] == false) {
            falseButton.isEnabled = false
            falseButton.isClickable = false
            trueButton.isEnabled = false
            trueButton.isClickable = false
        } else {
            falseButton.isEnabled = true
            falseButton.isClickable = true
            trueButton.isEnabled = true
            trueButton.isClickable = true
        }
    }

    private fun checkBlockMap() {
        mapAnswer[currentIndexThis] = false
        leftAnswer = leftAnswer!!.minus(1)
        updatePersentView(leftAnswer!!, persentPoints)
        coderBlockMap()
    }

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

    private fun decoderBlockMap() {
        for (i in 0 until numAnswer!!) {
            mapAnswer[j] = codeMap!![j] == '1'
            j++
        }
        j = 0
    }

    private fun useCheat() {
        cheatPoints -= 1
        cheatPointsLife.text = "Life = $cheatPoints"
        if (cheatPoints == 0) {
            cheatButton.isEnabled = false
            cheatButton.isClickable = false
        }
        isCheater = false
    }

    private fun resultTextView(points: Int) {
        persentPoints = points * 100 / numAnswer!!
        updatePersentView(leftAnswer!!, persentPoints)

        checkTimer = false

        loadPBAnswer(persentAnswer)
    }

    @SuppressLint("SetTextI18n")
    private fun updatePersentView(leftAnswer: Int, persentPoints: Int) {
        if (hardQuestion) viewResult.text = "(I don`t no) % Осталось - $leftAnswer"
        else viewResult.text = "$persentPoints % Осталось - $leftAnswer"
    }

    private fun loadFrontList() {
        mainViewModel.getCrime()

        mainViewModel.allCrime.observe(this, { item ->
            listCrime.clear()
            item.forEach {
                listCrime.add(
                    ListCrime(
                        it.id!!,
                        it.userName!!,
                        it.idNameQuiz,
                        it.data,
                        it.numQuestion!!,
                        it.persentPoints
                    )
                )
            }
            mainViewModel.getQuestionCrimeNewQuiz()
        })
        mainViewModel.allCrimeNewQuiz.observe(this, { it ->
            listCrimeNewQuiz.clear()
            it.forEach {
                listCrimeNewQuiz.add(
                    ListCrimeNewQuiz(
                        it.id!!,
                        it.nameQuestion,
                        it.answerQuestion,
                        it.typeQuestion,
                        it.idListNameQuestion
                    )
                )
            }
            mainViewModel.getFrontList()
        })
        mainViewModel.allFrontList.observe(this, { it ->
            listFrontList.clear()
            it.forEach {
                if (it.nameQuestion == idUser) {
                    Log.d("MainActivity", "____updateFrontList $updateFrontList")
                    updateFrontList++              //Костыль, потому что эта фн-я почему-то выполняется 2 раза
                    if (updateFrontList == 2) {

                        var frontList = FrontList(
                            it.id!!,
                            it.nameQuestion,
                            loadUserName(it.nameQuestion),
                            it.data,
                            loadStarsFun(it.nameQuestion),
                            quizListHQVar.size,
                            ((it.numA) + 1),
                            quizListHardQuestion.size,
                            it.starsAll + persentPoints
                        )
                        Log.d("MainActivity", "quizListHQVar.size - ${quizListHQVar.size}")
                        Log.d(
                            "MainActivity",
                            "quizListHardQuestion.size ${quizListHardQuestion.size}"
                        )
                        Log.d("MainActivity", "$frontList")
                        mainViewModel.updateFrontList(frontList)
                    }
                }
            }
        })
    }

    private fun loadStarsFun(question: String): Int {
        var num = 0
        var name = ""
        listCrime.forEach {
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

    private fun loadPoints(question: String): Int {
        var num = 0
        var name = ""
        listCrime.forEach {
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

    private fun loadUserName(question: String): String {
        var num = 0
        var name = ""
        listCrime.forEach {
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

    private fun loadStars(points: Int): Int = points / 100

    companion object {
        const val NAME_GEOQUIZ = "GeoQuiz"

        const val NAME_QUESTION = "name_question"
        const val NAME_USER = "name_user"
        const val STARS = "stars"
    }
}