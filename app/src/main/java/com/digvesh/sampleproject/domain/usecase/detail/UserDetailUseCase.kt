package com.digvesh.sampleproject.domain.usecase.detail

import com.digvesh.network.client.ApiResult
import com.digvesh.sampleproject.domain.contract.UserDataRepository
import com.digvesh.sampleproject.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDetailUseCase @Inject constructor(private val userDataRepository: UserDataRepository) {
    operator fun invoke(userId: Int): Flow<ApiResult<UserInfo>> =
        userDataRepository.getUserById(userId)
}