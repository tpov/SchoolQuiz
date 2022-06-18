package com.tpov.geoquiz.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tpov.geoquiz.R
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

        var anim3 = AnimationUtils.loadAnimation(this@SplashScreen, R.anim.anim_splash_v)
        animationListener(anim3, 3)
        tvV.startAnimation(anim3)
    }

    private fun animationListener(anim: Animation, numAnim: Int) {

        var toastEnd = Toast.makeText(this@SplashScreen, "EndAnimation", Toast.LENGTH_LONG)
        var toastStart = Toast.makeText(this@SplashScreen, "StartAnimation", Toast.LENGTH_LONG)

        anim.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                toastStart.show()
            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.tvT.visibility = View.GONE
                binding.tvP.visibility = View.GONE
                binding.tvO.visibility = View.GONE
                binding.tvV.visibility = View.GONE
                startActivity()
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })
    }


    private fun startActivity() {
        startActivity(Intent(this, FrontActivity::class.java))
    }


}