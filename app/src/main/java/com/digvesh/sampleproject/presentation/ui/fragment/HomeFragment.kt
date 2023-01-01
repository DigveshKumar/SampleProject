package com.digvesh.sampleproject.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.digvesh.core.extensions.putBundleInt
import com.digvesh.core.extensions.showToast
import com.digvesh.core.presentation.ui.fragment.BaseFragment
import com.digvesh.sampleproject.R
import com.digvesh.sampleproject.core.Constants.PARAM_USER_ID
import com.digvesh.sampleproject.databinding.FragmentHomeBinding
import com.digvesh.sampleproject.domain.model.UserInfo
import com.digvesh.sampleproject.presentation.ui.activity.MainActivity
import com.digvesh.sampleproject.presentation.ui.adapter.UserListAdapter
import com.digvesh.sampleproject.presentation.viewmodel.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>() {
    private lateinit var binding: FragmentHomeBinding
    private var page: Int = 1
    @Inject lateinit var userListAdapter : UserListAdapter

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
            viewModel.fetchUsersList(page)
            collectDataFromStateFlow(this)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun handleSuccessState(result: Any) {
        handleLoadingState(false)
        updateUI(result as List<UserInfo>)
    }

    override fun handleErrorState(errorCode: Int) {
        handleLoadingState(false)
        showToast(processErrorMessage(errorCode,requireContext()))
    }

    override fun handleLoadingState(isLoading: Boolean) {
        (activity as MainActivity).toggleProgressBar(isLoading)
    }

    private fun updateUI(data: List<UserInfo>) {
        handleLoadingState(false)
        userListAdapter.differ.submitList(data)
    }

    private fun navigateToDetailScreen(userId: Int) {
        putBundleInt(PARAM_USER_ID, userId)
        findNavController(this).navigate(R.id.action_home_to_DetailFragment, arguments)
    }

    override fun getViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }
}