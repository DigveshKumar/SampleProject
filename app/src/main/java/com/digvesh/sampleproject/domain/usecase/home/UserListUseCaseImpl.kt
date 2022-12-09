package com.digvesh.sampleproject.domain.usecase.home

import com.digvesh.sampleproject.data.repository.UserDataRepository
import javax.inject.Inject

class UserListUseCaseImpl @Inject constructor(private val userDataRepository: UserDataRepository) :
    UserListUseCase {
    override fun getUserList(page: Int) = userDataRepository.getUserList(page)
}