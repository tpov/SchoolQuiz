package com.tpov.geoquiz.activity

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationBuilderWithBuilderAccessor
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.*
import com.tpov.geoquiz.R
import com.tpov.geoquiz.activity.workers.RefreshDataWorker
import com.tpov.geoquiz.activity.workers.RefreshDataWorker.Companion.CHANNEL_ID
import com.tpov.geoquiz.database.MainViewModel
import com.tpov.geoquiz.databinding.ActivitySplashScreenBinding
import com.tpov.geoquiz.entities.EntityGenerateQuestion
import kotlinx.coroutines.InternalCoroutinesApi
import java.nio.channels.Channel
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("CustomSplashScreen")
@InternalCoroutinesApi
class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private var numQuestionNotDate = 0
    private var numSystemDate = false
    private var ckeckLoadApi = false
    private lateinit var generateQuestion: EntityGenerateQuestion
    private lateinit var generateQuestionNotNetwork: EntityGenerateQuestion
    private var questionNotNetwork: String = ""
    private var answerNotNetwork: String = ""
    private var questionNotNetworkDate: String = ""
    private var answerNotNetworkDate: String = ""
    private var numQuestionInList = 0

    private var questionApiArray: Array<String>? = null
    private var answerApiArray: Array<String>? = null

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }
    /*private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(this)
    }*/
    /*val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "loaded") {
                val percent = intent.getIntExtra("percent", 0)
                binding.tvQuestion.text = "$percent, %"
            }
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("WorkManager", "Начало")
        visibleTPOV(false)
        mainViewModel.getGenerateQuestion()

        /*val intentFilter = IntentFilter().apply {
            addAction("loaded")
        }*/
        /*localBroadcastManager.registerReceiver(receiver, intentFilter)*/
        checkQuestionNotDate(loadDate())
    }

   /* override fun onDestroy() {
        super.onDestroy()
        localBroadcastManager.unregisterReceiver(receiver)
    }*/

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
        numQuestionNotDate = 0
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
                    numQuestionNotDate++
                    questionNotNetwork = it.questionTranslate
                    answerNotNetwork = it.answerTranslate
                    generateQuestionNotNetwork = generateQuestion
                    Log.d("WorkManager", "найден пустой квиз")
                }
                if (it.date == systemDate || numQuestionNotDate == 9 && !numSystemDate) {
                    Log.d("WorkManager", "Создаем 10 квиз с датой")
                    mainViewModel.updateGenerationQuestion(
                        generateQuestion.copy(date = systemDate)
                    )        //Если из первых девяти вопросов не найдено который нужно отобразить, мы назначаем вопрос для отображения 10й

                    numSystemDate = true
                    binding.tvQuestion.text = it.questionTranslate
                    binding.tvAnswer.text = it.answerTranslate
                    questionNotNetworkDate = it.questionTranslate
                    answerNotNetworkDate = it.answerTranslate
                    createAnimation()
                }

            }

            Log.d("WorkManager", "Пустых вопросов $numQuestionNotDate")
            if (numQuestionNotDate < 10) {
                if (numQuestionNotDate == 0) {
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
    private fun loadNotification(title: String, name: String) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(name)
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()
        notificationManager.notify(1, notification)
    }

    private fun loadApi() {
        Log.d("WorkManager", "Создание воркера.")
        val workManager = WorkManager.getInstance(application)

        val requeust2 = RefreshDataWorker.makeRequest(numQuestionNotDate)

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
                        loadNotification("Ошибка", "Вопросы не были загружены, ошибка сети.")
                        if (numQuestionNotDate in 1..9) {
                            Log.d("WorkManager", "Создаем последний свободный вопрос")
                            mainViewModel.updateGenerationQuestion(
                                generateQuestionNotNetwork.copy(date = loadDate())
                            )        //Если из первых девяти вопросов не найдено который нужно отобразить, мы назначаем вопрос для отображения 10й

                            numSystemDate = true
                            if (questionNotNetworkDate != "") {
                                binding.tvQuestion.text = questionNotNetworkDate
                                binding.tvAnswer.text = answerNotNetworkDate
                            } else {
                                binding.tvQuestion.text = questionNotNetwork
                                binding.tvAnswer.text = answerNotNetwork
                                numQuestionNotDate--
                            }

                            createAnimation()
                            visibleTPOV(true)
                        } else {
                            binding.tvQuestion.text = "У вас закончились вопросы"
                        }
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
        Log.d("WorkManager", "Передаем - $numQuestionNotDate")
        loadNotification("Загрузка", "Подключение к сети")
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
                numQuestionInList++
            }
        }
        loadNotification("Успех","Загружено: $numQuestionInList вопросов")
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
        intent.putExtra(FrontActivity.NUM_QUESTION_NOT_NUL, numQuestionNotDate)
        startActivity(intent)
        finish()
    }

    companion object {
        const val NUM_QUESTION = "numQuestion"
        const val CHANNEL_ID = "channel_id"
        const val CHANNEL_NAME = "load_question"
    }
}