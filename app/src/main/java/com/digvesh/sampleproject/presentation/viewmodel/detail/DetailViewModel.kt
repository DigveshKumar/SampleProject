package com.digvesh.sampleproject.presentation.viewmodel.detail

import androidx.lifecycle.viewModelScope
import com.digvesh.core.presentation.ui.ViewState
import com.digvesh.core.presentation.ui.viewmodel.BaseViewModel
import com.digvesh.network.client.ApiResult
import com.digvesh.sampleproject.domain.model.UserInfo
import com.digvesh.sampleproject.domain.usecase.detail.UserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: UserDetailUseCase
) : BaseViewModel() {

    fun fetchUserDetails(userId: Int) {
        viewModelScope.launch {
            fetchUserDetail(userId)
        }
    }

    private suspend fun fetchUserDetail(userId: Int) {
        useCase.invoke(userId).collect {
            when (it) {
                is ApiResult.Success ->
                    it.result?.let { user ->
                        viewStateFlow.value =
                            ViewState.StateSuccess(user)
                    } ?: kotlin.run {
                        viewStateFlow.value = ViewState.StateError(
                            UserInfo(),
                            it.msg ?: "",
                            it.errorCode
                        )
                    }
                is ApiResult.Fail -> viewStateFlow.value =
                    ViewState.StateError(
                        UserInfo(),
                        it.msg ?: "",
                        it.errorCode
                    )
                is ApiResult.Pending -> viewStateFlow.value = ViewState.StateLoading(true)
            }
        }
    }
}