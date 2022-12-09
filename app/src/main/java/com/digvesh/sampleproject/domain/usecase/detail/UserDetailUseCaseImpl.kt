package com.digvesh.sampleproject.domain.usecase.detail

import com.digvesh.sampleproject.data.repository.UserDataRepository
import javax.inject.Inject

class UserDetailUseCaseImpl @Inject constructor(private val userDataRepository: UserDataRepository) :
    UserDetailUseCase {
    override fun getUserById(userId: Int) = userDataRepository.getUserById(userId)
}