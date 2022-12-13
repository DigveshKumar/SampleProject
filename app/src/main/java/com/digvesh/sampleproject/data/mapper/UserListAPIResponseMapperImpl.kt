package com.digvesh.sampleproject.data.mapper

import com.digvesh.sampleproject.data.mapper.contract.UserListAPIResponseMapper
import com.digvesh.sampleproject.data.model.Data
import com.digvesh.sampleproject.data.model.User
import com.digvesh.sampleproject.data.model.UsersListResponse
import com.digvesh.sampleproject.domain.model.UserInfo

class UserListAPIResponseMapperImpl : UserListAPIResponseMapper {
    override fun transformToUserList(response: UsersListResponse?): List<UserInfo> {
        val list: ArrayList<UserInfo> = arrayListOf()
        response?.data?.let {
            for (item in it) {
                list.add(transformToUserInfo(item))
            }
        }
        return list
    }

    override fun transformToUserInfo(response: Data?): UserInfo {
        val userInfo = UserInfo()
        response?.let {
            userInfo.id = it.id
            userInfo.firstName = it.firstName
            userInfo.lastName = it.lastName
            userInfo.displayName = getUserDisplayName(firstName = it.firstName, it.lastName)
            userInfo.email = it.email
            userInfo.avatarImage = it.avatar
        }
        return userInfo
    }

    override fun getUserInfo(response: User?): UserInfo {
        val userInfo = UserInfo()
        response?.data?.let {
            userInfo.id = it.id
            userInfo.firstName = it.firstName
            userInfo.lastName = it.lastName
            userInfo.displayName = getUserDisplayName(firstName = it.firstName, it.lastName)
            userInfo.email = it.email
            userInfo.avatarImage = it.avatar
        }
        return userInfo
    }

    private fun getUserDisplayName(firstName: String?, lastName: String?): String {
        return when {
            firstName == null -> lastName ?: ""
            lastName == null -> firstName
            else -> String.format("%s %s", firstName, lastName)
        }
    }
}
