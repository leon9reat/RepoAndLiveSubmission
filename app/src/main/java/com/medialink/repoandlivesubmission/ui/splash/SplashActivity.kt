package com.medialink.repoandlivesubmission.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.medialink.repoandlivesubmission.databinding.ActivitySplashBinding
import com.medialink.repoandlivesubmission.ui.main.MainActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    private lateinit var mScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(activitySplashBinding.root)

        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        mScope = CoroutineScope(Dispatchers.IO)
        withCoroutine(3_000L)
    }

    private fun launchPostSplashActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun withCoroutine(delay: Long) {
        mScope.launch {
            delay(delay)
            withContext(Dispatchers.Main) {
                launchPostSplashActivity()
                mScope.cancel(null)
            }
        }
    }

    private fun withHandler(delay: Long) {
        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({
            launchPostSplashActivity()
        }, delay) // 3000 is the delayed time in milliseconds.
    }
}