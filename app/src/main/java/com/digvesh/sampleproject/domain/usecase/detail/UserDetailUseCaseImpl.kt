package com.digvesh.sampleproject.domain.usecase.detail

import com.digvesh.sampleproject.data.repository.contract.UserDataRepository
import com.digvesh.sampleproject.domain.usecase.detail.contract.UserDetailUseCase
import javax.inject.Inject

class UserDetailUseCaseImpl @Inject constructor(private val userDataRepository: UserDataRepository) :
    UserDetailUseCase {
    override fun getUserById(userId: Int) = userDataRepository.getUserById(userId)
}