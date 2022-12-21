package com.digvesh.sampleproject.presentation.ui.activity

import android.os.Bundle
import androidx.core.view.isVisible
import com.digvesh.core.presentation.ui.activity.BaseActivity
import com.digvesh.sampleproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun toggleProgressBar(isLoading: Boolean = false) {
        binding.loadingView.progressView.isVisible = isLoading
    }
}