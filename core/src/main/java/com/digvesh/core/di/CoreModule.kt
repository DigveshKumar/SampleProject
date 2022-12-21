package com.digvesh.core.di

import com.digvesh.network.client.RestClientProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class CoreModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = RestClientProvider.buildRestClient()

}