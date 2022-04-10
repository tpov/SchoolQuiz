package com.tpov.geoquiz.activity

import android.app.Application
import com.tpov.geoquiz.database.CrimeDatabase
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.InternalCoroutinesApi

class MainApp: Application() {
    @InternalCoroutinesApi
    val database by lazy {
        CrimeDatabase.getDatabase(this)
    }
}