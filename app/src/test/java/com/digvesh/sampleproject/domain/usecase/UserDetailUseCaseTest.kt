package com.digvesh.sampleproject.domain.usecase

import com.digvesh.sampleproject.Constants.userId
import com.digvesh.sampleproject.data.repository.contract.UserDataRepository
import com.digvesh.sampleproject.domain.usecase.detail.contract.UserDetailUseCase
import com.digvesh.sampleproject.domain.usecase.detail.UserDetailUseCaseImpl
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
        useCase = UserDetailUseCaseImpl(repository)
    }

    fun testGetUserById() {
        runBlocking {
            coEvery { repository.getUserById(userId) } returns flow {}
            useCase.getUserById(userId)
            coVerify { repository.getUserById(userId) }
        }
    }
}