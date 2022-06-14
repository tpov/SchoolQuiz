package com.tpov.geoquiz.activity

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tpov.geoquiz.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        createAnimation()

    }

    private fun createAnimation() = with(binding) {
        tvT.startAnimation(AnimationUtils.loadAnimation(this@SplashScreen, R.anim.anim_splash_t))
        tvP.startAnimation(AnimationUtils.loadAnimation(this@SplashScreen, R.anim.anim_splash_p))
        tvO.startAnimation(AnimationUtils.loadAnimation(this@SplashScreen, R.anim.anim_splash_o))
        tvV.startAnimation(AnimationUtils.loadAnimation(this@SplashScreen, R.anim.anim_splash_v))
        tvSplash2.startAnimation(AnimationUtils.loadAnimation(this@SplashScreen, R.anim.next_question1))/*
        var anim3 = AnimationUtils.loadAnimation(this@SplashScreen, R.anim.anim_splash3_alfa)
        animationListener(anim3, 3)
        tvV.startAnimation(anim3)*/
    }

    private fun animationListener(anim: Animation, numAnim: Int) {

        var toastEnd = Toast.makeText(this@SplashScreen, "EndAnimation", Toast.LENGTH_LONG)
        var toastStart = Toast.makeText(this@SplashScreen, "StartAnimation", Toast.LENGTH_LONG)

        anim.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                toastStart.show()
            }

            override fun onAnimationEnd(p0: Animation?) {


                when(numAnim) {
                    3 -> toastEnd.show()
                }
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })
    }


    private fun startActivity() {
        startActivity(Intent(this, FrontActivity::class.java))
    }


}