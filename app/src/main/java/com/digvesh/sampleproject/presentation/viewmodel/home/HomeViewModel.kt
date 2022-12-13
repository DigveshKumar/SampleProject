package com.digvesh.sampleproject.presentation.viewmodel.home

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.digvesh.core.presentation.BaseViewModel
import com.digvesh.core.presentation.ui.ViewState
import com.digvesh.network.client.ApiResult
import com.digvesh.sampleproject.R
import com.digvesh.sampleproject.domain.model.UserInfo
import com.digvesh.sampleproject.domain.usecase.home.UserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: UserListUseCase
) : BaseViewModel() {
    private val _viewState = MutableStateFlow<ViewState<List<UserInfo>>>(
        ViewState.StateLoading(emptyList())
    )
    val viewState = _viewState.asStateFlow()

    fun fetchUsersList(context: Context, page: Int) {
        viewModelScope.launch {
            fetchUserList(context, page)
        }
    }

    private suspend fun fetchUserList(context: Context, page: Int) {
        useCase.getUserList(page).collectLatest {
            when (it) {
                is ApiResult.Fail -> _viewState.value =
                    ViewState.StateError(
                        emptyList(),
                        it.msg ?: context.getString(R.string.data_error)
                    )
                is ApiResult.Success ->
                    it.result?.let { list ->
                        _viewState.value =
                            ViewState.StateSuccess(list)
                    }
                is ApiResult.Pending -> _viewState.value = ViewState.StateLoading(emptyList())
            }
        }
    }
}