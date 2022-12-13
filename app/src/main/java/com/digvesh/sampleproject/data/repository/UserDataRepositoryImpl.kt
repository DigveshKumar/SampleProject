package com.digvesh.sampleproject.data.repository

import com.digvesh.network.client.ApiResult
import com.digvesh.sampleproject.data.api.UserDataService
import com.digvesh.sampleproject.data.mapper.contract.UserListAPIResponseMapper
import com.digvesh.sampleproject.data.repository.contract.UserDataRepository
import com.digvesh.sampleproject.domain.model.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val userDataService: UserDataService,
    private val userListAPIResponseMapper: UserListAPIResponseMapper
) : UserDataRepository {

    override fun getUserList(page: Int): Flow<ApiResult<List<UserInfo>>> =
        flow {
            val response = userDataService.getUsers(page)
            when {
                response.isSuccessful -> emit(
                    ApiResult.Success(
                        userListAPIResponseMapper.transformToUserList(
                            response.body()
                        )
                    )
                )
                else -> emit(ApiResult.Fail(response.message()))
            }
        }.flowOn(Dispatchers.IO)

    override fun getUserById(userId: Int): Flow<ApiResult<UserInfo>> =
        flow {
            val response = userDataService.getUserById(userId)
            when {
                response.isSuccessful -> emit(
                    ApiResult.Success(
                        userListAPIResponseMapper.getUserInfo(
                            response.body()
                        )
                    )
                )
                else -> emit(ApiResult.Fail(response.message()))
            }
        }.flowOn(Dispatchers.IO)
}