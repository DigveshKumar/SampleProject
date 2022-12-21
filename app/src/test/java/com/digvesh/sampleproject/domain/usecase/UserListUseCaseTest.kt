package com.digvesh.sampleproject.domain.usecase

import com.digvesh.sampleproject.Constants.page
import com.digvesh.sampleproject.domain.contract.UserDataRepository
import com.digvesh.sampleproject.domain.usecase.home.UserListUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class UserListUseCaseTest : TestCase() {

    private lateinit var useCase: UserListUseCase

    @MockK
    private lateinit var repository: UserDataRepository

    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        useCase = UserListUseCase(repository)
    }

    fun testGetUserList() {
        runBlocking {
            coEvery { repository.getUserList(page) } returns flow {}
            useCase.invoke(page)
            coVerify { repository.getUserList(page) }
        }
    }
}