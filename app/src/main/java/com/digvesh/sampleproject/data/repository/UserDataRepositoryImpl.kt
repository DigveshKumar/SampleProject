package com.digvesh.sampleproject.data.repository

import com.digvesh.network.client.ApiResult
import com.digvesh.network.client.NetworkConstants.STATUS_ERROR_DEFAULT
import com.digvesh.network.client.NetworkConstants.STATUS_NETWORK_ERROR
import com.digvesh.sampleproject.data.api.UserDataService
import com.digvesh.sampleproject.data.mapper.UserListAPIResponseMapper
import com.digvesh.sampleproject.domain.contract.UserDataRepository
import com.digvesh.sampleproject.domain.model.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
                else -> emit(ApiResult.Fail(STATUS_ERROR_DEFAULT, response.message()))
            }
        }.catch {
            emit(ApiResult.Fail(STATUS_NETWORK_ERROR))
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
                else -> emit(ApiResult.Fail(STATUS_NETWORK_ERROR, response.message()))
            }
        }.catch {
            emit(ApiResult.Fail(STATUS_NETWORK_ERROR))
        }.flowOn(Dispatchers.IO)
}