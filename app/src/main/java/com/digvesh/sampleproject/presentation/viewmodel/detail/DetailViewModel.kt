package com.digvesh.sampleproject.presentation.viewmodel.detail

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.digvesh.core.presentation.BaseViewModel
import com.digvesh.core.presentation.ui.ViewState
import com.digvesh.network.client.ApiResult
import com.digvesh.sampleproject.R
import com.digvesh.sampleproject.domain.model.UserInfo
import com.digvesh.sampleproject.domain.usecase.detail.contract.UserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: UserDetailUseCase
) : BaseViewModel() {
    private val _viewState = MutableStateFlow<ViewState<UserInfo>>(
        ViewState.StateLoading(UserInfo())
    )
    val viewState = _viewState.asStateFlow()

    fun fetchUserDetails(context: Context, userId: Int) {
        viewModelScope.launch {
            fetchUserDetail(context, userId)
        }
    }

    private suspend fun fetchUserDetail(context: Context, userId: Int) {
        useCase.getUserById(userId).collectLatest {
            when (it) {
                is ApiResult.Success ->
                    it.result?.let { user ->
                        _viewState.value =
                            ViewState.StateSuccess(user)
                    }
                is ApiResult.Fail -> _viewState.value =
                    ViewState.StateError(
                        UserInfo(),
                        it.msg ?: context.getString(R.string.data_error)
                    )
                is ApiResult.Pending -> _viewState.value = ViewState.StateLoading(UserInfo())
            }
        }
    }
}