package com.digvesh.sampleproject.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.digvesh.core.extensions.loadImage
import com.digvesh.core.extensions.showToast
import com.digvesh.core.presentation.ui.fragment.BaseFragment
import com.digvesh.sampleproject.core.Constants.PARAM_USER_ID
import com.digvesh.sampleproject.databinding.FragmentDetailBinding
import com.digvesh.sampleproject.domain.model.UserInfo
import com.digvesh.sampleproject.presentation.ui.activity.MainActivity
import com.digvesh.sampleproject.presentation.viewmodel.detail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailViewModel>() {
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configViews()
    }

    private fun configViews() {
        val userId = arguments?.getInt(PARAM_USER_ID)
        userId?.let {
            fetchData(it)
        }
    }

    private fun fetchData(userId: Int) {
        lifecycleScope.launch {
            viewModel.fetchUserDetails(userId)
            collectDataFromStateFlow(this)
        }
    }

    override fun handleErrorState(errorCode: Int) {
        handleLoadingState(false)
        showToast(processErrorMessage(errorCode,requireContext()))
    }

    private fun updateUI(data: UserInfo) {
        with(binding) {
            loadImage(context, data.avatarImage, userImage)
            userName.text = data.displayName
            userEmail.text = data.email
        }
    }

    override fun handleSuccessState(result: Any) {
        handleLoadingState(false)
        updateUI(result as UserInfo)
    }

    override fun handleLoadingState(isLoading: Boolean) {
        (activity as MainActivity).toggleProgressBar(isLoading)
    }

    override fun getViewModelClass(): Class<DetailViewModel> {
        return DetailViewModel::class.java
    }

}