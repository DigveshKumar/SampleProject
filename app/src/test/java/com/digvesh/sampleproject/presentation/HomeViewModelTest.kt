package com.digvesh.sampleproject.presentation

import android.content.Context
import com.digvesh.network.client.ApiResult
import com.digvesh.sampleproject.Constants.errorRetrievingData
import com.digvesh.sampleproject.domain.model.UserInfo
import com.digvesh.sampleproject.domain.usecase.home.UserListUseCase
import com.digvesh.sampleproject.presentation.viewmodel.home.HomeViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule

@ExperimentalCoroutinesApi
class HomeViewModelTest : TestCase() {
    @MockK
    private lateinit var userListUseCase: UserListUseCase

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    val mainCoroutineRule = CoroutineScopeTestWatcher()

    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = HomeViewModel(userListUseCase)
    }

    public override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
    }

    fun testFetchUserListSuccess() {
        val list = getUserList()
        runTest {
            coEvery { userListUseCase.getUserList(1) } returns (flow {
                emit(ApiResult.Success(list))
            })
            val context = mockk<Context>()
            viewModel.fetchUsersList(context, 1)
        }
        assertEquals(list, viewModel.viewState.value.result)
    }

    fun testFetchUserListFail() {
        runTest {
            coEvery { userListUseCase.getUserList(1) } returns (flow {
                emit(ApiResult.Fail(errorRetrievingData))
            })
            val context = mockk<Context>()
            viewModel.fetchUsersList(context, 1)
        }
        assertEquals(errorRetrievingData, viewModel.viewState.value.msg)
    }

    fun testFetchUserListLoading() {
        runTest {
            coEvery { userListUseCase.getUserList(1) } returns (flow {
                emit(ApiResult.Pending())
            })
            val context = mockk<Context>()
            viewModel.fetchUsersList(context, 1)
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }

    private fun getUserList(): List<UserInfo> {
        val list = mutableListOf<UserInfo>()
        val userInfo = UserInfo()
        list.add(userInfo)
        return list
    }
}