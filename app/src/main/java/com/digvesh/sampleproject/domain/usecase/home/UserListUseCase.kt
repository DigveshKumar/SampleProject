package com.digvesh.sampleproject.domain.usecase.home

import com.digvesh.network.client.ApiResult
import com.digvesh.sampleproject.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserListUseCase {
    fun getUserList(page: Int): Flow<ApiResult<List<UserInfo>>>
}