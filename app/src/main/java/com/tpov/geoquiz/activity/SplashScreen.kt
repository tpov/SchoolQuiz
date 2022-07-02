package com.tpov.geoquiz.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.tpov.geoquiz.R
import com.tpov.geoquiz.activity.workers.RefreshDataWorker
import com.tpov.geoquiz.database.MainViewModel
import com.tpov.geoquiz.databinding.ActivitySplashScreenBinding
import com.tpov.geoquiz.entities.EntityGenerateQuestion
import kotlinx.coroutines.InternalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("CustomSplashScreen")
@InternalCoroutinesApi
class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private var numQuestionNotNull = 0
    private var numSystemDate = false
    private var ckeckLoadApi = false
    private lateinit var generateQuestion: EntityGenerateQuestion

    private var questionApiArray: Array<String>? = null
    private var answerApiArray: Array<String>? = null

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("WorkManager", "Начало")
        visibleTPOV(false)
        mainViewModel.getGenerateQuestion()
        checkQuestionNotDate(loadDate())
    }

    private fun visibleTPOV(visible: Boolean) = with(binding) {
        Log.d("WorkManager", "Видимость ТПОВ.")
        if (visible) {
            tvT.visibility = View.VISIBLE
            tvP.visibility = View.VISIBLE
            tvO.visibility = View.VISIBLE
            tvV.visibility = View.VISIBLE
        } else {
            tvT.visibility = View.GONE
            tvP.visibility = View.GONE
            tvO.visibility = View.GONE
            tvV.visibility = View.GONE
        }
    }

    private fun loadDate(): String {
        Log.d("WorkManager", "Загрузка даты.")
        val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }

    private fun checkQuestionNotDate(systemDate: String) {
        mainViewModel.allGenerateQuestion.observe(this, { item ->
            Log.d("WorkManager", "Загрузка из бд.")
            item.forEach { it ->
                generateQuestion = EntityGenerateQuestion(
                    it.id,
                    it.date,
                    it.question,
                    it.answer,
                    it.questionTranslate,
                    it.answerTranslate
                )
                Log.d("WorkManager", "${it.date}")
                if (it.date == "0") {
                    numQuestionNotNull++
                    Log.d("WorkManager", "найден пустой квиз")
                }
                if (it.date == systemDate || numQuestionNotNull == 9 && !numSystemDate) {
                    Log.d("WorkManager", "Создаем 10 квиз с датой")
                    mainViewModel.updateGenerationQuestion(
                        generateQuestion.copy(date = systemDate)
                    )        //Если из первых девяти вопросов не найдено который нужно отобразить, мы назначаем вопрос для отображения 10й

                    numSystemDate = true
                    binding.tvSplash2.text = it.questionTranslate
                    createAnimation()
                    visibleTPOV(true)
                }

            }
            Log.d("WorkManager", "Пустых вопросов $numQuestionNotNull")
            if (numQuestionNotNull < 10) {
                if (numQuestionNotNull == 0) {
                    Toast.makeText(
                        this,
                        "Пожалуйста подождите, вопросы загружаются с сервера (~1Мб)",
                        Toast.LENGTH_LONG
                    ).show()
                }
                Log.d("WorkManager", "Пустых вопросов меньше 10, загружаем еще раз")
                loadApi()
                ckeckLoadApi = true
            }
        })

    }

    private fun loadApi() {
        Log.d("WorkManager", "Создание воркера.")
        val workManager = WorkManager.getInstance(application)

        val requeust2 = RefreshDataWorker.makeRequest(numQuestionNotNull)

        workManager.getWorkInfoByIdLiveData(requeust2.id)
            .observe(this, {
                Log.d("WorkManager", "finish воркер")
                if (it.state.isFinished) {
                    Log.d(
                        "WorkManager",
                        "${it.progress.getStringArray(RefreshDataWorker.QUESTION)}, " +
                                "${it.outputData.getStringArray(RefreshDataWorker.QUESTION)}, Принимаем данные из воркера"
                    )

                    questionApiArray = it.outputData.getStringArray(RefreshDataWorker.QUESTION)
                    answerApiArray = it.outputData.getStringArray(RefreshDataWorker.ANSWER)


                    if (questionApiArray != null) {
                        Log.d("WorkManager", "Квест апи не пустой")
                        loadQuestion()
                    } else {
                        Log.d("WorkManager", "Квест апи пустой")
                        Toast.makeText(
                            this,
                            "Не удалось подключится к интернету",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        createAnimation()
                    }
                }
            })
        Log.d("WorkManager", "Передаем - $numQuestionNotNull")
        workManager.enqueue(requeust2)


    }

    private fun loadQuestion() {

        Log.d("WorkManager", "Загрузка квеста.")
        var list = mutableListOf(
            EntityGenerateQuestion(null, "", "", "", "", ""),
            EntityGenerateQuestion(null, "", "", "", "", ""),
            EntityGenerateQuestion(null, "", "", "", "", ""),
            EntityGenerateQuestion(null, "", "", "", "", ""),
            EntityGenerateQuestion(null, "", "", "", "", ""),
            EntityGenerateQuestion(null, "", "", "", "", ""),
            EntityGenerateQuestion(null, "", "", "", "", ""),
            EntityGenerateQuestion(null, "", "", "", "", ""),
            EntityGenerateQuestion(null, "", "", "", "", ""),
            EntityGenerateQuestion(null, "", "", "", "", "")
        )
        for (i in 0..9) {
            if (questionApiArray!![i] != "") {
                Log.d("WorkManager", "Найдет не пустой квест, $i")
                var entityGenerateQuestion = EntityGenerateQuestion(
                    null,
                    "0",
                    questionApiArray!![i],
                    answerApiArray!![i],
                    questionApiArray!![i],
                    answerApiArray!![i]
                )
                list[i] = entityGenerateQuestion
            }
        }
        mainViewModel.insertGenerationQuestion(list)
        Log.d("WorkManager", "Закончилась загрузка квеста")
        Log.d("WorkManager", "ищем еще раз")
        Thread.sleep(250)
        mainViewModel.getGenerateQuestion()
    }

    private fun createAnimation() = with(binding) {
        visibleTPOV(true)

        tvT.startAnimation(AnimationUtils.loadAnimation(this@SplashScreen, R.anim.anim_splash_t))
        tvP.startAnimation(AnimationUtils.loadAnimation(this@SplashScreen, R.anim.anim_splash_p))
        tvO.startAnimation(AnimationUtils.loadAnimation(this@SplashScreen, R.anim.anim_splash_o))

        var anim3 = AnimationUtils.loadAnimation(this@SplashScreen, R.anim.anim_splash_v)
        animationListener(anim3, 3)
        tvV.startAnimation(anim3)
    }

    private fun animationListener(anim: Animation, numAnim: Int) {

        var toastEnd = Toast.makeText(this@SplashScreen, "EndAnimation", Toast.LENGTH_LONG)
        var toastStart = Toast.makeText(this@SplashScreen, "StartAnimation", Toast.LENGTH_LONG)

        anim.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                visibleTPOV(true)
            }

            override fun onAnimationEnd(p0: Animation?) {
                visibleTPOV(false)
                startActivity()
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })
    }

    private fun startActivity() {
        var intent = Intent(this, FrontActivity::class.java)
        intent.putExtra(FrontActivity.NUM_QUESTION_NOT_NUL, numQuestionNotNull)
        startActivity(intent)
        finish()
    }

    companion object {
        const val NUM_QUESTION = "numQuestion"
    }
}