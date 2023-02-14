package com.tpov.schoolquiz.di

import androidx.lifecycle.ViewModel
import com.tpov.schoolquiz.di.ViewModelKey
import com.tpov.schoolquiz.presentation.mainactivity.MainActivityViewModel
import com.tpov.schoolquiz.presentation.network.profile.ProfileViewModel
import com.tpov.schoolquiz.presentation.question.QuestionViewModel
import com.tpov.schoolquiz.presentation.splashscreen.SplashScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.InternalCoroutinesApi

@Module
interface ViewModelModule {

    @InternalCoroutinesApi
    @Binds
    @IntoMap
    @ViewModelKey(QuestionViewModel::class)
    fun bindQuestionViewModel(viewModel: QuestionViewModel): ViewModel

    @InternalCoroutinesApi
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    fun bindMainViewModel(viewModel: MainActivityViewModel): ViewModel

    @InternalCoroutinesApi
    @Binds
    @IntoMap
    @ViewModelKey(SplashScreenViewModel::class)
    fun bindSplashScreenViewModel(viewModel: SplashScreenViewModel): ViewModel
    @InternalCoroutinesApi
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

}