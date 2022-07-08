package com.tpov.geoquiz.Services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast
import com.tpov.geoquiz.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MyService : Service() {
    private var player: MediaPlayer? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        if (player == null) {
            player = MediaPlayer.create(this@MyService, R.raw.music_wot)
        }

        coroutineScope.launch {
            player?.start()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        player?.stop()
        coroutineScope.cancel()
    }
}