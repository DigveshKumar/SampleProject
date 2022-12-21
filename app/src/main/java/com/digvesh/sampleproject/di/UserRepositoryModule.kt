package com.digvesh.sampleproject.di

import com.digvesh.sampleproject.data.mapper.UserListAPIResponseMapper
import com.digvesh.sampleproject.data.repository.UserDataRepositoryImpl
import com.digvesh.sampleproject.domain.contract.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UserRepositoryModule : AppModule() {
    @Provides
    fun provideUserListRepository(): UserDataRepository = UserDataRepositoryImpl(
        provideApiService(provideRetrofit()), UserListAPIResponseMapper()
    )
}