package com.digvesh.core.presentation.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.digvesh.core.presentation.ui.ViewState
import com.digvesh.network.client.NetworkConstants.STATUS_NETWORK_ERROR
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {

    val viewStateFlow = MutableStateFlow<ViewState<Any>>(
        ViewState.StateLoading(true)
    )

    val viewState = viewStateFlow.asStateFlow()

    fun processErrorMessage(errorCode: Int, context: Context): String {
        return when (errorCode) {
            STATUS_NETWORK_ERROR -> context.getString(com.digvesh.network.R.string.network_error)
            else -> context.getString(com.digvesh.network.R.string.data_error)
        }
    }
}