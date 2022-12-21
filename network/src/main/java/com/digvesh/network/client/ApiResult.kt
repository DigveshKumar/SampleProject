package com.digvesh.network.client

sealed class ApiResult<T>(val errorCode: Int = 0, val result: T? = null, val msg: String? = null) {
    class Success<T>(data: T?) : ApiResult<T>(0, data)
    class Fail<T>(errorCode: Int, msg: String? = null) : ApiResult<T>(errorCode, null, msg)
    class Pending<T> : ApiResult<T>()
}