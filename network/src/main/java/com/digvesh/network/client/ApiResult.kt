package com.digvesh.network.client

sealed class ApiResult<T>(val result: T? = null, val msg: String? = null) {
    class Success<T>(data: T) : ApiResult<T>(data)
    class Fail<T>(msg: String) :  ApiResult<T>(null, msg)
    class Pending<T> : ApiResult<T>()
}