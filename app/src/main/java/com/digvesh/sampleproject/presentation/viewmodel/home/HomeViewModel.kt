package com.digvesh.sampleproject.presentation.viewmodel.home

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.digvesh.core.presentation.ui.ViewState
import com.digvesh.core.presentation.ui.viewmodel.BaseViewModel
import com.digvesh.network.client.ApiResult
import com.digvesh.sampleproject.domain.model.UserInfo
import com.digvesh.sampleproject.domain.usecase.home.UserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: UserListUseCase
) : BaseViewModel() {

    fun fetchUsersList(context: Context, page: Int) {
        viewModelScope.launch {
            fetchUserList(context, page)
        }
    }

    private suspend fun fetchUserList(context: Context, page: Int) {
        useCase.invoke(page).collect {
            when (it) {
                is ApiResult.Fail -> viewStateFlow.value =
                    ViewState.StateError(
                        listOf<UserInfo>(),
                        it.msg ?: processErrorMessage(it.errorCode, context)
                    )
                is ApiResult.Success ->
                    it.result?.let { list ->
                        viewStateFlow.value =
                            ViewState.StateSuccess(list)
                    } ?: kotlin.run {
                        viewStateFlow.value = ViewState.StateError(
                            listOf<UserInfo>(),
                            it.msg ?: processErrorMessage(it.errorCode, context)
                        )
                    }
                is ApiResult.Pending -> viewStateFlow.value = ViewState.StateLoading(true)
            }
        }
    }
}