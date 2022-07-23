package com.tpov.schoolquiz.activity

import android.app.Application
import com.tpov.schoolquiz.database.CrimeDatabase
import kotlinx.coroutines.InternalCoroutinesApi

class MainApp: Application() {
    @InternalCoroutinesApi
    val database by lazy {
        CrimeDatabase.getDatabase(this)
    }
}