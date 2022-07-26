package com.tpov.schoolquiz.activity

import android.app.Application
import com.tpov.schoolquiz.data.database.QuizDatabase
import kotlinx.coroutines.InternalCoroutinesApi

class MainApp: Application() {
    @InternalCoroutinesApi
    val database by lazy {
        QuizDatabase.getDatabase(this)
    }
}