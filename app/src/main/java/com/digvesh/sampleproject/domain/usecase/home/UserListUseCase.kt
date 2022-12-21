package com.digvesh.sampleproject.domain.usecase.home

import com.digvesh.network.client.ApiResult
import com.digvesh.sampleproject.domain.contract.UserDataRepository
import com.digvesh.sampleproject.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserListUseCase @Inject constructor(private val userDataRepository: UserDataRepository) {
    operator fun invoke(page: Int): Flow<ApiResult<List<UserInfo>>> =
        userDataRepository.getUserList(page)
}