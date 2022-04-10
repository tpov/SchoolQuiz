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
    lateinit var binding: FrontActivityBinding
    private var idNameQuiz = ""
    private var time = ""
    private var numQuestion = 0
    private var points = 0
    private var frontList: FrontList? = null
    private var closeDialog: Boolean = false
    private var iAd: InterstitialAd? = null

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),

        Question(R.string.question_riverInRussia, true),
        Question(R.string.question_headquarters, true),
        Question(R.string.question_Gabon, false),
        Question(R.string.question_Sicily, true),
        Question(R.string.question_Ireland, true),
        Question(R.string.question_Paris, true),
        Question(R.string.question_Atlas, false),
        Question(R.string.question_China, true),
        Question(R.string.question_Mayan, false),
        Question(R.string.question_Reykjavik, true),
        Question(R.string.question_Oymyakon, true)
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
                false,
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