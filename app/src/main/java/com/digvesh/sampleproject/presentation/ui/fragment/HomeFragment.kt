package com.digvesh.sampleproject.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.digvesh.core.presentation.ViewState
import com.digvesh.core.presentation.ui.BaseFragment
import com.digvesh.sampleproject.R
import com.digvesh.sampleproject.core.Constants.PARAM_USER_ID
import com.digvesh.sampleproject.databinding.FragmentHomeBinding
import com.digvesh.sampleproject.domain.model.UserInfo
import com.digvesh.sampleproject.presentation.ui.adapter.UserListAdapter
import com.digvesh.sampleproject.presentation.viewmodel.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private var page: Int = 1
    private val viewModel: HomeViewModel by viewModels()
    private val userListAdapter = UserListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
    }

    private fun configureViews() {
        with(binding.rvUserList) {
            layoutManager = LinearLayoutManager(context)
            userListAdapter.onClickListener = object :
                UserListAdapter.OnClickListener {
                override fun onClick(userId: Int?) {
                    userId?.let { navigateToDetailScreen(it) }
                }
            }
            adapter = userListAdapter
        }

        fetchData()
    }

    private fun fetchData() {
        lifecycleScope.launch {
            viewModel.fetchUsersList(requireContext(), page)
            viewModel.viewState.collect { viewState ->
                when (viewState) {
                    is ViewState.StateError -> handleErrorState(viewState.msg)
                    is ViewState.StateLoading -> handleLoadingState()
                    is ViewState.StateSuccess -> handleSuccessState(viewState.result)
                }
            }
        }
    }

    private fun handleSuccessState(result: List<UserInfo>) {
        hideLoading()
        updateUI(result)
    }

    private fun handleErrorState(errorMsg: String) {
        hideLoading()
        Toast.makeText(
            requireContext(),
            errorMsg,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun handleLoadingState() {
        showLoading()
    }

    private fun updateUI(data: List<UserInfo>) {
        hideLoading()
        userListAdapter.differ.submitList(data)
    }

    private fun navigateToDetailScreen(userId: Int) {
        val bundle = Bundle()
        bundle.putInt(PARAM_USER_ID, userId)
        findNavController(this).navigate(R.id.action_home_to_DetailFragment, bundle)
    }

    private fun showLoading() {
        with(binding) {
            progressView.isVisible = true
        }
    }

    private fun hideLoading() {
        with(binding) {
            progressView.isVisible = false
        }
    }

}