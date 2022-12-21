package com.digvesh.sampleproject.domain.contract

import com.digvesh.network.client.ApiResult
import com.digvesh.sampleproject.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    fun getUserList(page: Int): Flow<ApiResult<List<UserInfo>>>
    fun getUserById(userId: Int): Flow<ApiResult<UserInfo>>
}