package com.digvesh.sampleproject.data.mapper

import com.digvesh.sampleproject.Constants.avatarImage
import com.digvesh.sampleproject.Constants.displayName
import com.digvesh.sampleproject.Constants.email
import com.digvesh.sampleproject.Constants.firstName
import com.digvesh.sampleproject.Constants.lastName
import com.digvesh.sampleproject.Constants.page
import com.digvesh.sampleproject.Constants.supportUrl
import com.digvesh.sampleproject.Constants.text
import com.digvesh.sampleproject.Constants.userId
import com.digvesh.sampleproject.data.model.Data
import com.digvesh.sampleproject.data.model.Support
import com.digvesh.sampleproject.data.model.User
import com.digvesh.sampleproject.data.model.UsersListResponse
import com.digvesh.sampleproject.domain.model.UserInfo
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase

class UserListAPIResponseMapperTest : TestCase() {

    private lateinit var userListAPIResponseMapper: UserListAPIResponseMapper

    public override fun setUp() {
        super.setUp()
        userListAPIResponseMapper = UserListAPIResponseMapper()
    }

    fun testTransformToUserList() {
        assertEquals(
            userListAPIResponseMapper.transformToUserList(getUserListResponse()),
            arrayListOf(userListAPIResponseMapper.transformToUserInfo(getUserInfo().data))
        )
    }

    fun testGetUserInfoFromNullResponse() {
        assertEquals(UserInfo(), userListAPIResponseMapper.getUserInfo(null))
    }

    fun testUserInfo() {
        assertEquals(userListAPIResponseMapper.getUserInfo(getUserInfo()).id, userId)
        assertEquals(userListAPIResponseMapper.getUserInfo(getUserInfo()).firstName, firstName)
        assertEquals(userListAPIResponseMapper.getUserInfo(getUserInfo()).lastName, lastName)
        assertEquals(userListAPIResponseMapper.getUserInfo(getUserInfo()).displayName, displayName)
        assertEquals(userListAPIResponseMapper.getUserInfo(getUserInfo()).email, email)
        assertEquals(userListAPIResponseMapper.getUserInfo(getUserInfo()).avatarImage, avatarImage)
    }

    private fun getUserInfo(): User {
        val user = mockk<User>()
        val data = mockk<Data>()
        val support = mockk<Support>()

        coEvery { data.id } returns userId
        coEvery { data.firstName } returns firstName
        coEvery { data.lastName } returns lastName
        coEvery { data.email } returns email
        coEvery { data.avatar } returns avatarImage

        coEvery { support.url } returns supportUrl
        coEvery { support.text } returns text

        coEvery { user.data } returns data
        coEvery { user.support } returns support

        return user
    }

    private fun getUserListResponse(): UsersListResponse {
        val usersListResponse = mockk<UsersListResponse>()
        coEvery { usersListResponse.page } returns page
        coEvery { usersListResponse.perPage } returns page
        coEvery { usersListResponse.total } returns page
        coEvery { usersListResponse.totalPages } returns page
        val list = mutableListOf<Data>()
        val data = mockk<Data>()
        coEvery { data.id } returns userId
        coEvery { data.firstName } returns firstName
        coEvery { data.lastName } returns lastName
        coEvery { data.email } returns email
        coEvery { data.avatar } returns avatarImage
        val support = mockk<Support>()
        coEvery { support.url } returns supportUrl
        coEvery { support.text } returns text
        list.add(data)
        coEvery { usersListResponse.data } returns list
        coEvery { usersListResponse.support } returns support
        return usersListResponse
    }
}