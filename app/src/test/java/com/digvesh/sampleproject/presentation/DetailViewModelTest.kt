package com.digvesh.sampleproject.presentation

import com.digvesh.network.client.ApiResult
import com.digvesh.sampleproject.Constants.errorRetrievingData
import com.digvesh.sampleproject.Constants.networkErrorCode
import com.digvesh.sampleproject.Constants.userId
import com.digvesh.sampleproject.domain.model.UserInfo
import com.digvesh.sampleproject.domain.usecase.detail.UserDetailUseCase
import com.digvesh.sampleproject.presentation.viewmodel.detail.DetailViewModel
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
class DetailViewModelTest : TestCase() {
    @MockK
    private lateinit var userDetailUseCase: UserDetailUseCase
    private lateinit var viewModel: DetailViewModel

    @get:Rule
    val mainCoroutineRule = CoroutineScopeTestWatcher()

    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = DetailViewModel(userDetailUseCase)
    }

    public override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
    }

    fun testApiGetUserDetailSuccess() {
        runTest {
            coEvery { userDetailUseCase.invoke(1) } returns (flow {
                val userInfo = mockk<UserInfo>()
                coEvery { userInfo.id } returns 1
                emit(ApiResult.Success(userInfo))
            })
            viewModel.fetchUserDetails(1)
        }
        assertEquals(1, (viewModel.viewState.value.result as UserInfo).id)
    }

    fun testApiGetUserDetailSuccessButUserNull() {
        runTest {
            coEvery { userDetailUseCase.invoke(1) } returns (flow {
                emit(ApiResult.Success(null))
            })
            viewModel.fetchUserDetails(1)
        }
        assertEquals(-1, (viewModel.viewState.value.result as UserInfo).id)
    }

    fun testApiGetUserDetailFail() {
        runTest {
            coEvery { userDetailUseCase.invoke(userId) } returns (flow {
                emit(ApiResult.Fail(networkErrorCode, errorRetrievingData))
            })
            viewModel.fetchUserDetails(userId)
        }
        assertEquals(errorRetrievingData, viewModel.viewState.value.msg)
    }

    fun testApiGetUserDetailLoading() {
        runTest {
            coEvery { userDetailUseCase.invoke(userId) } returns (flow { emit(ApiResult.Pending()) })
            viewModel.fetchUserDetails(userId)
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }
}