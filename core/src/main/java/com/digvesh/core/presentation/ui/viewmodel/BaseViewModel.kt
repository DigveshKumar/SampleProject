package com.digvesh.core.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.digvesh.core.presentation.ui.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {

    val viewStateFlow = MutableStateFlow<ViewState<Any>>(
        ViewState.StateLoading(true)
    )

    val viewState = viewStateFlow.asStateFlow()
}