package com.tpov.schoolquiz.di

import android.app.Application
import com.tpov.schoolquiz.data.RepositoryImpl
import com.tpov.schoolquiz.data.database.QuizDao
import com.tpov.schoolquiz.data.database.QuizDatabase
import com.tpov.schoolquiz.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.InternalCoroutinesApi

@Module
interface DataModule {

    @InternalCoroutinesApi
    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

    companion object {

        @InternalCoroutinesApi
        @Provides
        fun provideGetQuizDao(
            application: Application        //Нужно добавить в граф зависимостей
        ): QuizDao {
            return QuizDatabase.getDatabase(application).getQuizDao()
        }
    }
}