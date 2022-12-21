package com.digvesh.sampleproject.di

import com.digvesh.core.di.CoreModule
import com.digvesh.sampleproject.data.api.UserDataService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class AppModule : CoreModule() {

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): UserDataService =
        retrofit.create(UserDataService::class.java)

}