package com.digvesh.sampleproject.data.mapper

import com.digvesh.sampleproject.data.model.Data
import com.digvesh.sampleproject.data.model.User
import com.digvesh.sampleproject.data.model.UsersListResponse
import com.digvesh.sampleproject.domain.model.UserInfo

class UserListAPIResponseMapper {
    fun transformToUserList(response: UsersListResponse?): List<UserInfo> {
        val list: ArrayList<UserInfo> = arrayListOf()
        response?.data?.let {
            for (item in it) {
                list.add(transformToUserInfo(item))
            }
        }
        return list
    }

    fun transformToUserInfo(response: Data?): UserInfo {
        var userInfo = UserInfo()
        response?.let {
            userInfo = UserInfo(
                it.id,
                it.firstName,
                it.lastName,
                getUserDisplayName(firstName = it.firstName, it.lastName),
                it.email,
                it.avatar
            )
        }
        return userInfo
    }

    fun getUserInfo(response: User?): UserInfo {
        response?.data?.let {
            return transformToUserInfo(it)
        }
        return UserInfo()
    }

    private fun getUserDisplayName(firstName: String?, lastName: String?): String {
        return when {
            firstName == null -> lastName ?: ""
            lastName == null -> firstName
            else -> String.format("%s %s", firstName, lastName)
        }
    }
}
