package com.tpov.schoolquiz.presentation.question

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
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.tpov.schoolquiz.*
import com.tpov.schoolquiz.data.Services.MusicService
import com.tpov.schoolquiz.databinding.ActivityQuestionBinding
import com.tpov.schoolquiz.presentation.MainApp
import com.tpov.schoolquiz.presentation.factory.ViewModelFactory
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

private const val REQUEST_CODE_CHEAT = 0
/**
 * This activity contains many variables that are needed to restore the session and the information processing logic.
 * High WTF/min
 * Refractoring incomplete
 * First, there is a check to see if the quest that the player wants to complete is not completed, if not, 
 * then he goes through it from the beginning. 
 * To save the session, many variables and encodings are used that are made from objects in one line, 
 * this allows you to have the entire progress of the passage, save it and restore it.
 */
@InternalCoroutinesApi
class QuestionActivity : AppCompatActivity() {

    lateinit var viewModel: QuestionViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by lazy {
        ActivityQuestionBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as MainApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        viewModel = ViewModelProvider(this, viewModelFactory)[QuestionViewModel::class.java]
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        var nameQuestionUser = intent.getStringExtra(NAME_QUESTION)
        viewModel.userName = intent.getStringExtra(NAME_USER)
        viewModel.stars = intent.getIntExtra(STARS, 0)

        Log.d("intent", "${viewModel.stars}")
        viewModel.idUser = nameQuestionUser!!

        viewModel.insertQuiz()
        viewModel.inits()
        viewModel.hardQuestion = viewModel.getHardQuestion(viewModel.stars)
        viewModel.getUpdateQuiz(viewModel.idUser)
        viewModel.getQuizList()

        binding.apply {
            if (viewModel.hardQuestion) {
                viewBackground.setBackgroundResource(R.color.background_hard_question)
                cheatButton.visibility = View.GONE
            }
            trueButton.setOnClickListener {
                viewModel.trueButton()
            }
            falseButton.setOnClickListener { _: View ->
                viewModel.falseButton()
            }

            cheatButton.setOnClickListener { view ->
                val answerIsTrue = viewModel.currentQuestionAnswer
                val intent = CheatActivity.newIntent(this@QuestionActivity, answerIsTrue)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val options =
                        ActivityOptions.makeClipRevealAnimation(view, 0, 0, view.width, view.height)
                    startActivityForResult(intent, REQUEST_CODE_CHEAT, options.toBundle())
                } else {
                    startActivityForResult(intent, REQUEST_CODE_CHEAT)
                }
            }

            nextButton.setOnClickListener {
                viewModel.nextButton()
            }
            prefButton.setOnClickListener {
                viewModel.prefButton()
            }
        }
        actionBarSettings()
        startService(Intent(this, MusicService::class.java))
        startObserve()
    }

    private fun startObserve() {
        viewModel.getInfoQuestion()

        loadBPAnswer()
        getQuizList()
        //showToast()
        springAnimLiveData()
        moveToPref()
        moveToNext()
        loadResultTimer()
        checkBlock()

        cheatPointLife()
        cheatButtonLiveData()
        questionText()
        viewResult()
        loadTimer()
    }

    private fun springAnimLiveData() {
        viewModel.springAnim.observe(this) {
            springAnim(it)
        }
    }

    private fun viewResult() {
        viewModel.viewResultLiveData.observe(this) {
            binding.viewResult.text = it
        }
    }

    private fun questionText() {
        viewModel.questionTextViewLiveData.observe(this) {
            binding.questionTextView.text = it
        }
    }

    private fun cheatButtonLiveData() {
        viewModel.cheatButtonLiveData.observe(this) {
            binding.cheatButton.isClickable = it
            binding.cheatButton.isEnabled = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, MusicService::class.java))
    }

    private fun cheatPointLife() {
        viewModel.cheatPointsLiveData.observe(this) {
            binding.cheatPointsLife.text = it
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()

            R.id.item_auto_update_answer -> {
                item.isChecked = !item.isChecked
                viewModel.updateAnswer = if (item.isChecked) {
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
                if (!viewModel.hardQuestion) {
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

                        questionActivityIntent.putExtra(
                            EXTRA_CURRENT_INDEX,
                            viewModel.currentIndex
                        )   //Output
                        questionActivityIntent.putExtra(EXTRA_CODE_ANSWER, viewModel.codeAnswer)
                        questionActivityIntent.putExtra(EXTRA_CODE_ID_USER, viewModel.idUser)
                        startActivityForResult(questionActivityIntent, UPDATE_CURRENT_INDEX)
                    } else {
                        questionActivityIntent.putExtra(
                            EXTRA_UPDATE_CURRENT_INDEX,
                            viewModel.currentIndex
                        )   //Output
                        startActivityForResult(questionActivityIntent, UPDATE_CURRENT_INDEX)
                    }
                }
            }
        }
        return true
    }

    private fun springAnim(next: Boolean) = with(binding) {
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

    private fun actionBarSettings() {       //Кнопка назад в баре
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    private fun loadTimer() = with(binding) {
        viewModel.loadTimerLiveData.observe(this@QuestionActivity) {
            if (!viewModel.checkTimer) {

                viewModel.startGame(viewModel.mapAnswer[viewModel.currentIndex]!!)
                viewModel.formattedTime.observe(this@QuestionActivity) {

                    //Если тамер доходит до значения 00:03, то делаем анимацию которая отображает цифры 3,2,1 на весь экран
                    tvTimer.text = it
                    if (it[3] == '0' && it[4] == '3') anim321(3) //Анимация для цифры 3
                    if (it[3] == '0' && it[4] == '2') anim321(2) //2
                    if (it[3] == '0' && it[4] == '1') anim321(1) //1
                }
                viewModel.checkTimer = true
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun anim321(num: Int) = with(binding) {
        tv321.text = num.toString()

        var anim = AnimationUtils.loadAnimation(this@QuestionActivity, R.anim.time_3_2_1)
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

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            viewModel.isCheater = data.getBooleanExtra(EXTRA_ANSWER_SHOW, false)
        } else if (requestCode == UPDATE_CURRENT_INDEX) {
            viewModel.currentIndex = data.getIntExtra(EXTRA_UPDATE_CURRENT_INDEX, 0)

        }
        viewModel.updateQuestion()
        viewModel.checkBlock()
        viewModel.loadTimer()
    }

    // TODO: 26.07.2022 Delete!
    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString("codeMap", viewModel.codeMap)
            putString("codeAnswer", viewModel.codeAnswer)
            putInt("currentIndex", viewModel.currentIndexThis)
            putInt("constCurrentIndex", viewModel.constCurrentIndex)
            putInt("points", viewModel.points)
            putInt("persentPoints", viewModel.percentPoints)
            putBoolean("isCheater", viewModel.isCheater)
            putInt("cheatPoints", viewModel.cheatPoints)
            putInt("leftAnswer", viewModel.leftAnswer!!)
            putInt("numQuestion", viewModel.numQuestion!!)
            putInt("numAnswer", viewModel.numAnswer!!)
            putBoolean("updateAnswer", viewModel.updateAnswer)
            putBoolean("insertCrime", viewModel.insertQuiz)
            putBoolean("insertCrimeNewQuiz", viewModel.loadedQuestion)
            putString("charMap", viewModel.charMap)
            putInt("i", viewModel.i)
            putInt("j", viewModel.j)
            putInt("idCrime", viewModel.idQuiz)
            putString("userName", viewModel.userName)
            putString("idUser", viewModel.idUser)
            putBoolean("hardQuestion", viewModel.hardQuestion)
            putInt("updateFrontList", viewModel.updateQuiz)
            putInt("stars", viewModel.stars)
            putInt("persentAnswer", viewModel.percentAnswer)
            putInt("currentIndex", viewModel.currentIndex)
            putBoolean("checkTimer", viewModel.checkTimer)
        }
        super.onSaveInstanceState(outState)
    }

    @SuppressLint("SetTextI18n")
    override fun onRestoreInstanceState(saveInstanceState: Bundle) {
        super.onRestoreInstanceState(saveInstanceState)
        viewModel.codeMap = saveInstanceState.getString("codeMap")!!
        viewModel.codeAnswer = saveInstanceState.getString("codeAnswer")!!
        viewModel.currentIndexThis = saveInstanceState.getInt("currentIndex")
        viewModel.points = saveInstanceState.getInt("points")
        viewModel.percentPoints = saveInstanceState.getInt("persentPoints")
        viewModel.isCheater = saveInstanceState.getBoolean("isCheater")
        viewModel.cheatPoints = saveInstanceState.getInt("cheatPoints")
        viewModel.leftAnswer = saveInstanceState.getInt("leftAnswer")
        viewModel.numQuestion = saveInstanceState.getInt("numQuestion")
        viewModel.numAnswer = saveInstanceState.getInt("numAnswer")
        viewModel.updateAnswer = saveInstanceState.getBoolean("updateAnswer")
        viewModel.insertQuiz = saveInstanceState.getBoolean("insertCrime")
        viewModel.loadedQuestion = saveInstanceState.getBoolean("insertCrimeNewQuiz")
        viewModel.charMap = saveInstanceState.getString("charMap")
        viewModel.i = saveInstanceState.getInt("i")
        viewModel.j = saveInstanceState.getInt("j")
        viewModel.idQuiz = saveInstanceState.getInt("idCrime")
        viewModel.userName = saveInstanceState.getString("userName")
        viewModel.idUser = saveInstanceState.getString("idUser")!!
        viewModel.hardQuestion = saveInstanceState.getBoolean("hardQuestion")
        viewModel.updateQuiz = saveInstanceState.getInt("updateFrontList")
        viewModel.stars = saveInstanceState.getInt("stars")
        viewModel.percentAnswer = saveInstanceState.getInt("persentAnswer")
        viewModel.currentIndex = saveInstanceState.getInt("currentIndex")
        viewModel.checkTimer = saveInstanceState.getBoolean("checkTimer")
        viewModel.constCurrentIndex = saveInstanceState.getInt("constCurrentIndex")

        binding.vAndroid.text =

            "vAndroid - ${Build.VERSION.SDK_INT}, vCode - ${Build.VERSION_CODES.M}"
        viewModel.updatePersentView(viewModel.leftAnswer!!, viewModel.percentPoints)
        viewModel.decoderBlockMap()
        viewModel.checkBlock()
        viewModel.setQuizVar(true, false)
    }

    private fun loadResultTimer() {
        viewModel.loadResultTimerLiveData.observe(this) {
            viewModel.gameResult.observe(this) {
                if (!it) viewModel.endTimer()
            }
        }
        viewModel.loadResultTimer()
    }

    private fun loadBPAnswer() = with(binding) {
        viewModel.loadBDAnswerLiveData.observe(this@QuestionActivity) {
            viewModel.answerQuiz.observe(this@QuestionActivity) {
                if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    pbAnswer.setProgress(it, true)
                } else {
                    pbAnswer.progress = it
                }
            }
        }
    }

        //Загружаем вопросы
    private fun getQuizList() {
        viewModel.getQuizListLiveData.observe(this) {

            this.viewModel.getQuestion.observe(this) {
                if (viewModel.loadedQuestion) {     //Заглушка что-бы обсервер не работал когда нам не нужно
                    viewModel.getQuizLists(it)
                }
            }

        }
        }

    // TODO: 29.07.2022 -> viewModel
    private fun showToast() {
        viewModel.loadToastLiveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
        viewModel.lastToastLiveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
        viewModel.toastShowLiveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    //Проверка на блокировку кнопок
    private fun checkBlock() = with(binding) {
        viewModel.checkBlockLiveData.observe(this@QuestionActivity) {
            checkCurrentThis(it)
            falseButton.isEnabled = it
            falseButton.isClickable = it
            trueButton.isEnabled = it
            trueButton.isClickable = it
        }
    }

    private fun checkCurrentThis(it: Boolean) {
        if (it) viewModel.currentIndexThis = viewModel.currentIndex
    }

    private fun moveToPref() = with(binding) {

        viewModel.moveToPrevLiveData.observe(this@QuestionActivity) {
            var animPref1 =
                AnimationUtils.loadAnimation(this@QuestionActivity, R.anim.pref_question1)
            var animPref2 =
                AnimationUtils.loadAnimation(this@QuestionActivity, R.anim.pref_question2)

            animPref1.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {

                }

                override fun onAnimationEnd(p0: Animation?) {
                    questionTextView.visibility = View.GONE
                    viewModel.currentIndex = (viewModel.currentIndex - 1) % viewModel.numQuestion!!
                    viewModel.updateQuestion()


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
                    viewModel.checkBlock()
                }

                override fun onAnimationRepeat(p0: Animation?) {

                }
            })
            questionTextView.startAnimation(animPref1)
        }
    }

    private fun moveToNext() {
        viewModel.moveToNextLiveData.observe(this) {
            var animNext1 = AnimationUtils.loadAnimation(this, R.anim.next_question1)
            var animNext2 = AnimationUtils.loadAnimation(this, R.anim.next_question2)

            animNext1.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {

                }

                override fun onAnimationEnd(p0: Animation?) {
                    binding.questionTextView.visibility = View.GONE
                    viewModel.currentIndex = (viewModel.currentIndex + 1) % viewModel.numQuestion!!
                    viewModel.updateQuestion()

                    binding.questionTextView.startAnimation(animNext2)
                }

                override fun onAnimationRepeat(p0: Animation?) {

                }
            })

            animNext2.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                    binding.questionTextView.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(p0: Animation?) {
                    viewModel.checkBlock()
                }

                override fun onAnimationRepeat(p0: Animation?) {

                }
            })
            binding.questionTextView.startAnimation(animNext1)
        }
    }

    companion object {
        const val NAME_QUESTION = "name_question"
        const val NAME_USER = "name_user"
        const val STARS = "stars"
        const val UPDATE_CURRENT_INDEX = 1
    }
}
