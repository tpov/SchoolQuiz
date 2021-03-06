package com.tpov.geoquiz.activity

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
import com.tpov.geoquiz.Question
import com.tpov.geoquiz.R
import com.tpov.geoquiz.database.MainViewModel
import com.tpov.geoquiz.databinding.FrontActivityBinding
import com.tpov.geoquiz.entities.CrimeNewQuiz
import com.tpov.geoquiz.entities.FrontList
import com.tpov.geoquiz.fragment.FragmentManager
import com.tpov.geoquiz.settings.SettingsActivity
import com.tpov.shoppinglist.utils.TimeManager
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class FrontActivity : AppCompatActivity() {
    private lateinit var binding: FrontActivityBinding
    private var idNameQuiz = ""
    private var time = ""
    private var numQuestion = 0
    private var points = 0
    private var frontList: FrontList? = null
    private var closeDialog: Boolean = false
    private var iAd: InterstitialAd? = null

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
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FrontActivityBinding.inflate(layoutInflater)
        insertCrimeNewQuiz("GeoQuiz")
        //insertNameQuiz("GeoQuiz")
        setContentView(binding.root)

        setButtonNavListener()
        insertFrontList("GeoQuiz", "")

        FragmentManager.setFragment(FragmentTitle.newInstance(), this)
    }

    companion object {
        const val SHOP_LIST = "shop_list"
    }

    @InternalCoroutinesApi
    fun insertFrontList(listNameQuestion: String, listUserName: String) {
        val frontList = FrontList(
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
        mainViewModel.insertFrontList(frontList)
    }

    private fun insertCrimeNewQuiz(idQuiz: String) {
        var q = 0
        var textQ = ""
        questionBank.forEach {
            q++
            textQ = getString(it.textResId)
            var name = CrimeNewQuiz(
                null,
                getString(it.textResId),
                it.answer,
                it.typeQuestion,
                idQuiz
            )
            mainViewModel.insertCrimeNewQuiz(name)
        }
    }

    private fun setButtonNavListener() {

        binding.bNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                }
                R.id.menu_new_quiz -> {
                    FragmentManager.currentFrag?.onClickNew("", 0)
                   /* showInterAd(object: AdListener {
                        override fun onFinish() {

                        }
                    })*/

                }
                R.id.menu_settings -> {
                    startActivity(Intent(this@FrontActivity, SettingsActivity::class.java))
                    /*showInterAd(object: AdListener {
                        override fun onFinish() {

                        }
                    })*/
                }
                R.id.menu_info -> {
                    startActivity(Intent(this@FrontActivity, InfoActivity::class.java))
                   /* showInterAd(object: AdListener {
                        override fun onFinish() {

                        }
                    })*/
                }
                R.id.main_title_button -> {
                }
            }
            true
        }
    }

    private fun loadInterAd() {
        val request = AdRequest.Builder().build()
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", request, object : InterstitialAdLoadCallback() {

            override fun onAdLoaded(ad: InterstitialAd) {
                Log.d("MainActivity_bNav", "onAdLoaded")

                iAd = ad
            }
            override fun onAdFailedToLoad(ad: LoadAdError) {
                Log.d("MainActivity_bNav", "onAdFailedToLoad")

                iAd = null
            }
        } )
    }

    private fun showInterAd(adListener :AdListener) {
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
                    Toast.makeText(this@FrontActivity, R.string.massage_show_ads, Toast.LENGTH_LONG).show()
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
}