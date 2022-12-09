package com.digvesh.sampleproject.data.repository

import com.digvesh.sampleproject.Constants.errorString
import com.digvesh.sampleproject.Constants.userId
import com.digvesh.sampleproject.data.api.UserDataService
import com.digvesh.sampleproject.data.mapper.UserListAPIResponseMapper
import com.digvesh.sampleproject.data.model.User
import com.digvesh.sampleproject.data.model.UsersListResponse
import com.digvesh.sampleproject.domain.model.UserInfo
import com.digvesh.sampleproject.presentation.CoroutineScopeTestWatcher
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import retrofit2.Response

@ExperimentalCoroutinesApi
class UserDataRepositoryImplTest : TestCase() {

    private lateinit var userDataRepository: UserDataRepository

    @MockK
    private lateinit var userDataService: UserDataService

    @MockK
    private lateinit var userListAPIResponseMapper: UserListAPIResponseMapper

    @get:Rule
    val mainCoroutineRule = CoroutineScopeTestWatcher()

    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.IO)
        userDataRepository = UserDataRepositoryImpl(userDataService, userListAPIResponseMapper)
    }

    public override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
    }

    fun testAPIUsersListSuccess() {
        runTest {
            val response = mockk<Response<UsersListResponse>>()
            coEvery { response.isSuccessful } returns true
            val usersListResponse = mockk<UsersListResponse>()
            coEvery { response.body() } returns usersListResponse
            coEvery { userListAPIResponseMapper.transformToUserList(usersListResponse) } returns listOf(
                mockk()
            )
            coEvery { userDataService.getUsers(1) } returns response
            assertEquals(1, userDataRepository.getUserList(1).first().result?.size)
        }
    }

    fun testAPIUsersListFail() {
        runTest {
            val response = mockk<Response<UsersListResponse>>()
            coEvery { response.isSuccessful } returns false
            coEvery { response.message() } returns errorString
            coEvery { userDataService.getUsers(1) } returns response
            assertEquals(errorString, userDataRepository.getUserList(1).first().msg)
        }
    }


    fun testAPIGetUserByIdSuccess() {
        runTest {
            val id = userId
            val user = mockk<User>()
            val userInfo = mockk<UserInfo>()
            val response = mockk<Response<User>>()
            coEvery { response.isSuccessful } returns true
            coEvery { response.body() } returns user
            coEvery { userListAPIResponseMapper.getUserInfo(user) } returns userInfo
            coEvery { userDataService.getUserById(id) } returns response
            assertEquals(userInfo, userDataRepository.getUserById(id).first().result)
        }
    }

    fun testAPIGetUserByIdFail() {
        runTest {
            val id = userId
            val response = mockk<Response<User>>()
            coEvery { response.isSuccessful } returns false
            coEvery { response.message() } returns errorString
            coEvery { userDataService.getUserById(id) } returns response
            assertEquals(errorString, userDataRepository.getUserById(id).first().msg)
        }
    }
}