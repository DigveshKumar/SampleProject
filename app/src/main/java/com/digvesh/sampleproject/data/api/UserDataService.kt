package com.digvesh.sampleproject.data.api

import com.digvesh.sampleproject.data.model.User
import com.digvesh.sampleproject.data.model.UsersListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserDataService {
    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): Response<UsersListResponse>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<User>
}