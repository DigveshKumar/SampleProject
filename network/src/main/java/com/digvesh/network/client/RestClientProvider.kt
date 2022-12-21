package com.digvesh.network.client

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RestClientProvider {

    private const val BASE_URL = "https://reqres.in/api/"
    private const val connectTimeout: Long = 60
    private const val readTimeout: Long = 60
    private const val writeTimeout: Long = 60

    fun buildRestClient(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(getOkHttpClient())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val okHttpClient: OkHttpClient.Builder = OkHttpClient().newBuilder()
        okHttpClient.connectTimeout(connectTimeout, TimeUnit.SECONDS)
        okHttpClient.readTimeout(readTimeout, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(writeTimeout, TimeUnit.SECONDS)
        return okHttpClient.build()
    }
}