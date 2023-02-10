package com.tpov.schoolquiz.presentation.mainactivity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.tpov.schoolquiz.R
import com.tpov.schoolquiz.databinding.ActivityMainBinding
import com.tpov.schoolquiz.presentation.MainApp
import com.tpov.schoolquiz.presentation.factory.ViewModelFactory
import com.tpov.schoolquiz.presentation.fragment.FragmentManager
import com.tpov.schoolquiz.presentation.mainactivity.info.InfoActivity
import com.tpov.schoolquiz.presentation.network.profile.ContactFragment
import com.tpov.schoolquiz.presentation.network.profile.ContactFragment.Companion.PERMISSION_REQUEST_CONTACTS
import com.tpov.schoolquiz.presentation.network.profile.ContactFragment.Companion.permissions
import com.tpov.schoolquiz.presentation.settings.SettingsActivity
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

/**
 * This is the main screen of the application, it consists of a panel that shows how much spare is left.
 * questions of the day and a fragment that displays user and system questions
 */

@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var iAd: InterstitialAd? = null
    private var numQuestionNotDate = 0
    private lateinit var viewModel: MainActivityViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var recreateActivity: Boolean = false

    private val component by lazy {
        (application as MainApp).component
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Remove the action bar
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]
        insertQuestion("GeoQuiz")

        setButtonNavListener()
        insertQuizList("GeoQuiz", "")
        numQuestionNotDate = intent.getIntExtra(NUM_QUESTION_NOT_NUL, 0)
        FragmentManager.setFragment(FragmentMain.newInstance(), this)

        loadNumQuestionNotDate()
        
    }

    //Окраживаем квадратики в красный и зеленый в зависимости сколько осталось запасных вопросов-дня
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
    fun insertQuizList(listNameQuestion: String, listUserName: String) {
        val frontList = quiz(listNameQuestion, listUserName)
        viewModel.insertQuiz(frontList)
    }

    private fun insertQuestion(idQuiz: String) {
        var q = 0
        var textQ = ""
        viewModel.questionBank().forEach {
            q++
            textQ = getString(it.textResId)
            val name = com.tpov.schoolquiz.data.database.entities.Question(
                null,
                getString(it.textResId),
                it.answer,
                it.typeQuestion,
                idQuiz
            )
            viewModel.insertQuestion(name)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setButtonNavListener() {

        binding.bNav.setOnItemSelectedListener {
            //it.isChecked = true
            //val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.pulsation)
                //binding.bNav.startAnimation(animation)
                //true

            when (it.itemId) {
                R.id.menu_home -> {
                    FragmentManager.setFragment(FragmentMain.newInstance(), this)
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

                R.id.menu_network -> {
                    FragmentManager.setFragment(ContactFragment.newInstance(), this)
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
                    iAd = ad
                }

                override fun onAdFailedToLoad(ad: LoadAdError) {
                    iAd = null
                }
            })
    }

    private fun showInterAd(adListener: AdListener) {
        if (iAd != null) {

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
                    loadInterAd()
                }
            }
            iAd?.show(this)
        } else {
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