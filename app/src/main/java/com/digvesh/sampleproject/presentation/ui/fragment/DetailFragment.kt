package com.digvesh.sampleproject.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.digvesh.core.presentation.ui.ViewState
import com.digvesh.core.presentation.ui.BaseFragment
import com.digvesh.sampleproject.core.Constants.PARAM_USER_ID
import com.digvesh.sampleproject.databinding.FragmentDetailBinding
import com.digvesh.sampleproject.domain.model.UserInfo
import com.digvesh.sampleproject.presentation.viewmodel.detail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()

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
            viewModel.fetchUserDetails(requireContext(), userId)
            viewModel.viewState.collectLatest { viewState ->
                when (viewState) {
                    is ViewState.StateError -> handleErrorState(viewState.msg)
                    is ViewState.StateSuccess -> handleSuccessState(viewState.result)
                    is ViewState.StateLoading -> {}
                }
            }
        }
    }

    private fun handleSuccessState(result: UserInfo) {
        updateUI(result)
    }

    private fun handleErrorState(errorMsg: String) {
        Toast.makeText(
            requireContext(),
            errorMsg,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun updateUI(data: UserInfo) {
        with(binding) {
            lifecycleScope.async {
                Glide.with(userImage).load(data.avatarImage).into(userImage)
            }
            userName.text = data.displayName
            userEmail.text = data.email
        }
    }
}