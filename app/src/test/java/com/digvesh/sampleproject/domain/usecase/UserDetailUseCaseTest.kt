package com.digvesh.sampleproject.domain.usecase

import com.digvesh.sampleproject.Constants.userId
import com.digvesh.sampleproject.domain.contract.UserDataRepository
import com.digvesh.sampleproject.domain.usecase.detail.UserDetailUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class UserDetailUseCaseTest : TestCase() {

    private lateinit var useCase: UserDetailUseCase

    @MockK
    private lateinit var repository: UserDataRepository

    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        useCase = UserDetailUseCase(repository)
    }

    fun testGetUserById() {
        runBlocking {
            coEvery { repository.getUserById(userId) } returns flow {}
            useCase.invoke(userId)
            coVerify { repository.getUserById(userId) }
        }
    }
}