package com.tpov.schoolquiz.activity

import FragmentTitle
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.tpov.schoolquiz.R
import com.tpov.schoolquiz.presentation.question.QuestionViewModel
import com.tpov.schoolquiz.databinding.FrontActivityBinding
import com.tpov.schoolquiz.data.database.entities.Quiz
import com.tpov.schoolquiz.data.model.Question
import com.tpov.schoolquiz.fragment.FragmentManager
import com.tpov.schoolquiz.settings.SettingsActivity
import com.tpov.shoppinglist.utils.TimeManager
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: FrontActivityBinding

    private var idNameQuiz = ""
    private var time = ""
    private var numQuestion = 0
    private var points = 0
    private var quiz: Quiz? = null
    private var closeDialog: Boolean = false
    private var iAd: InterstitialAd? = null
    private var numQuestionNotDate = 0

    private val questionBank = listOf(
        Question(R.string.question_light1, true, false),
        Question(R.string.question_light2, true, false),
        Question(R.string.question_light3, false, false),
        Question(R.string.question_light4, false, false),
        Question(R.string.question_light5, true, false),
        Question(R.string.question_light6, true, false),

        Question(R.string.question_light7, true, false),
        Question(R.string.question_light8, true, false),
        Question(R.string.question_light9, false, false),
        Question(R.string.question_light10, true, false),
        Question(R.string.question_light11, true, false),
        Question(R.string.question_light12, true, false),
        Question(R.string.question_light13, false, false),
        Question(R.string.question_light14, true, false),
        Question(R.string.question_light15, false, false),
        Question(R.string.question_light16, true, false),

        Question(R.string.question_light17, true, false),
        Question(R.string.question_light18, true, false),
        Question(R.string.question_light19, false, false),
        Question(R.string.question_light20, false, false),

        Question(R.string.question_hard1, false, true),
        Question(R.string.question_hard2, false, true),
        Question(R.string.question_hard3, false, true),
        Question(R.string.question_hard4, true, true),
        Question(R.string.question_hard5, true, true),
        Question(R.string.question_hard6, false, true),
        Question(R.string.question_hard7, false, true),
        Question(R.string.question_hard8, true, true),
        Question(R.string.question_hard9, false, true),
        Question(R.string.question_hard10, false, true),
        Question(R.string.question_hard11, true, true),
        Question(R.string.question_hard12, false, true),
        Question(R.string.question_hard13, false, true),
        Question(R.string.question_hard14, false, true),
        Question(R.string.question_hard15, true, true),
        Question(R.string.question_hard16, false, true),

        Question(R.string.question_hard17, true, true),
        Question(R.string.question_hard18, true, true),
        Question(R.string.question_hard19, false, true),
        Question(R.string.question_hard20, false, true)
    )

    @InternalCoroutinesApi
    private val questionViewModel: QuestionViewModel by viewModels {
        QuestionViewModel.QuizModelFactory((applicationContext as MainApp).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FrontActivityBinding.inflate(layoutInflater)

        insertCrimeNewQuiz("GeoQuiz")
        setContentView(binding.root)

        setButtonNavListener()
        insertFrontList("GeoQuiz", "")
        numQuestionNotDate = intent.getIntExtra(NUM_QUESTION_NOT_NUL, 0)
        FragmentManager.setFragment(FragmentTitle.newInstance(), this)

        loadNumQuestionNotDate()
    }


    private fun loadNumQuestionNotDate() = with(binding) {
        if (numQuestionNotDate > 0) textView10.setBackgroundResource(R.color.num_chack_norice_green)
        if (numQuestionNotDate > 1) textView9.setBackgroundResource(R.color.num_chack_norice_green)
        if (numQuestionNotDate > 2) textView8.setBackgroundResource(R.color.num_chack_norice_green)
        if (numQuestionNotDate > 3) textView7.setBackgroundResource(R.color.num_chack_norice_green)
        if (numQuestionNotDate > 4) textView6.setBackgroundResource(R.color.num_chack_norice_green)
        if (numQuestionNotDate > 5) textView5.setBackgroundResource(R.color.num_chack_norice_green)
        if (numQuestionNotDate > 6) textView4.setBackgroundResource(R.color.num_chack_norice_green)
        if (numQuestionNotDate > 7) textView3.setBackgroundResource(R.color.num_chack_norice_green)
        if (numQuestionNotDate > 8) textView2.setBackgroundResource(R.color.num_chack_norice_green)
        if (numQuestionNotDate > 9) textView.setBackgroundResource(R.color.num_chack_norice_green)
    }

    @InternalCoroutinesApi
    fun insertFrontList(listNameQuestion: String, listUserName: String) {
        val frontList = Quiz(
            null,
            listNameQuestion,
            listUserName,
            TimeManager.getCurrentTime(),
            0,
            0,
            0,
            0,
            0
        )
        questionViewModel.insertQuiz(frontList)
    }

    private fun insertCrimeNewQuiz(idQuiz: String) {
        var q = 0
        var textQ = ""
        questionBank.forEach {
            q++
            textQ = getString(it.textResId)
            var name = com.tpov.schoolquiz.data.database.entities.Question(
                null,
                getString(it.textResId),
                it.answer,
                it.typeQuestion,
                idQuiz
            )
            questionViewModel.insertQuestion(name)
        }
    }

    private fun setButtonNavListener() {

        binding.bNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                }

                R.id.menu_new_quiz -> {
                    FragmentManager.currentFrag?.onClickNew("", 0)
                }

                R.id.menu_settings -> {
                    startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                }

                R.id.menu_info -> {
                    startActivity(Intent(this@MainActivity, InfoActivity::class.java))
                }
            }
            true
        }
    }

    private fun loadInterAd() {
        val request = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",
            request,
            object : InterstitialAdLoadCallback() {

                override fun onAdLoaded(ad: InterstitialAd) {
                    Log.d("MainActivity_bNav", "onAdLoaded")

                    iAd = ad
                }

                override fun onAdFailedToLoad(ad: LoadAdError) {
                    Log.d("MainActivity_bNav", "onAdFailedToLoad")

                    iAd = null
                }
            })
    }

    private fun showInterAd(adListener: AdListener) {
        if (iAd != null) {
            Log.d("MainActivity_bNav", "iAd != null")

            iAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    iAd = null
                    loadInterAd()
                    adListener.onFinish()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    iAd = null
                    loadInterAd()
                }

                override fun onAdShowedFullScreenContent() {
                    iAd = null
                    Toast.makeText(this@MainActivity, R.string.massage_show_ads, Toast.LENGTH_LONG)
                        .show()
                    Log.d("MainActivity_bNav", "onAdShowedFullScreenContent")

                    loadInterAd()
                }
            }
            iAd?.show(this)
        } else {
            Log.d("MainActivity_bNav", "iAd = null")
            adListener.onFinish()
        }
    }

    interface AdListener {
        fun onFinish()
    }

    companion object {
        const val NUM_QUESTION_NOT_NUL = "num_question_not_nul"
        const val SHOP_LIST = "shop_list"

    }
}