package com.digvesh.sampleproject.presentation

import android.content.Context
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
            val context = mockk<Context>()
            coEvery { userDetailUseCase.invoke(1) } returns (flow {
                val userInfo = mockk<UserInfo>()
                coEvery { userInfo.id } returns 1
                emit(ApiResult.Success(userInfo))
            })
            viewModel.fetchUserDetails(context, 1)
        }
        assertEquals(1, (viewModel.viewState.value.result as UserInfo).id)
    }

    fun testApiGetUserDetailSuccessButUserNull() {
        runTest {
            val context = mockk<Context>()
            coEvery {
                context.getString(any())
            } returns errorRetrievingData
            coEvery { userDetailUseCase.invoke(1) } returns (flow {
                val userInfo = mockk<UserInfo>()
                emit(ApiResult.Success(null))
            })
            viewModel.fetchUserDetails(context, 1)
        }
        assertEquals(-1, (viewModel.viewState.value.result as UserInfo).id)
    }

    fun testApiGetUserDetailFail() {
        runTest {
            val context = mockk<Context>()
            coEvery { userDetailUseCase.invoke(userId) } returns (flow {
                emit(ApiResult.Fail(networkErrorCode, errorRetrievingData))
            })
            viewModel.fetchUserDetails(context, userId)
        }
        assertEquals(errorRetrievingData, viewModel.viewState.value.msg)
    }

    fun testApiGetUserDetailLoading() {
        runTest {
            val context = mockk<Context>()
            coEvery { userDetailUseCase.invoke(userId) } returns (flow { emit(ApiResult.Pending()) })
            viewModel.fetchUserDetails(context, userId)
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }
}