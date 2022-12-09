package com.digvesh.sampleproject.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.digvesh.core.presentation.ui.BaseActivity
import com.digvesh.sampleproject.core.Constants.SPLASH_DELAY_MILLIS
import com.digvesh.sampleproject.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigateToMainScreenWithDelay()
    }

    private fun navigateToMainScreenWithDelay() {
        lifecycleScope.launch {
            delay(SPLASH_DELAY_MILLIS)
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}