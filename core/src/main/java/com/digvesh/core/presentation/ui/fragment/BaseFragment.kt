package com.digvesh.core.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.digvesh.core.presentation.ui.ViewState
import com.digvesh.core.presentation.ui.viewmodel.BaseViewModel
import com.digvesh.network.client.NetworkConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    lateinit var viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[getViewModelClass()]
        super.onCreate(savedInstanceState)
    }

    fun collectDataFromStateFlow(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            viewModel.viewStateFlow.collect { viewState ->
                when (viewState) {
                    is ViewState.StateError -> handleErrorState(viewState.errorCode)
                    is ViewState.StateLoading -> handleLoadingState(viewState.isLoading)
                    is ViewState.StateSuccess -> handleSuccessState(viewState.result)
                }
            }
        }
    }

    fun processErrorMessage(errorCode: Int, context: Context): String {
        return when (errorCode) {
            NetworkConstants.STATUS_NETWORK_ERROR -> context.getString(com.digvesh.network.R.string.network_error)
            else -> context.getString(com.digvesh.network.R.string.data_error)
        }
    }

    abstract fun getViewModelClass(): Class<T>
    abstract fun handleSuccessState(result: Any)
    abstract fun handleErrorState(errorCode: Int)
    abstract fun handleLoadingState(isLoading: Boolean = false)
}