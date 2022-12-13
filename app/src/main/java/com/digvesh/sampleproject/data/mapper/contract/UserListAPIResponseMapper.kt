package com.digvesh.sampleproject.data.mapper.contract

import com.digvesh.sampleproject.data.model.Data
import com.digvesh.sampleproject.data.model.User
import com.digvesh.sampleproject.data.model.UsersListResponse
import com.digvesh.sampleproject.domain.model.UserInfo

interface UserListAPIResponseMapper {
    fun transformToUserList(response: UsersListResponse?): List<UserInfo>
    fun transformToUserInfo(response: Data?): UserInfo
    fun getUserInfo(response: User?): UserInfo
}