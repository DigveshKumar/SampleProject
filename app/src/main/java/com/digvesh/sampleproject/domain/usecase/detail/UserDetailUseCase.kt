package com.digvesh.sampleproject.domain.usecase.detail

import com.digvesh.network.client.ApiResult
import com.digvesh.sampleproject.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserDetailUseCase {
    fun getUserById(userId: Int): Flow<ApiResult<UserInfo>>
}