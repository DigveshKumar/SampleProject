package com.digvesh.sampleproject.di

import com.digvesh.network.client.RestClientProvider
import com.digvesh.sampleproject.data.api.UserDataService
import com.digvesh.sampleproject.data.mapper.contract.UserListAPIResponseMapper
import com.digvesh.sampleproject.data.mapper.UserListAPIResponseMapperImpl
import com.digvesh.sampleproject.data.repository.contract.UserDataRepository
import com.digvesh.sampleproject.data.repository.UserDataRepositoryImpl
import com.digvesh.sampleproject.domain.usecase.detail.contract.UserDetailUseCase
import com.digvesh.sampleproject.domain.usecase.detail.UserDetailUseCaseImpl
import com.digvesh.sampleproject.domain.usecase.home.UserListUseCase
import com.digvesh.sampleproject.domain.usecase.home.UserListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideHomeScreenUseCase(): UserListUseCase =
        UserListUseCaseImpl(provideUserListRepository())

    @Provides
    fun provideUserDetailUseCase(): UserDetailUseCase =
        UserDetailUseCaseImpl(provideUserListRepository())

    @Provides
    fun provideUserListRepository(): UserDataRepository = UserDataRepositoryImpl(
        provideApiService(provideRetrofit()), providesUserListResponseMapper()
    )

    private fun providesUserListResponseMapper(): UserListAPIResponseMapper {
        return UserListAPIResponseMapperImpl()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient = RestClientProvider.getOkHttpClient()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = RestClientProvider.buildRestClient()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): UserDataService =
        retrofit.create(UserDataService::class.java)

}