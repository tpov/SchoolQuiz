package com.tpov.schoolquiz.di

import android.app.Application
import com.tpov.schoolquiz.presentation.mainactivity.FragmentMain
import com.tpov.schoolquiz.presentation.mainactivity.MainActivity
import com.tpov.schoolquiz.presentation.question.QuestionActivity
import com.tpov.schoolquiz.presentation.question.QuestionListActivity
import com.tpov.schoolquiz.presentation.splashscreen.SplashScreen
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.InternalCoroutinesApi

@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
@InternalCoroutinesApi
interface ApplicationComponent {

    fun inject(activity: QuestionActivity)

    fun inject(activity: QuestionListActivity)

    fun inject(fragment: FragmentMain)

    fun inject(activity: SplashScreen)

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {     //Граф зависимостей

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}