package com.digvesh.core.presentation

sealed class ViewState<T>(val result: T, val msg: String, val isLoading: Boolean) {
    class StateSuccess<T>(data: T) : ViewState<T>(data, "", false)
    class StateError<T>(result: T, msg: String) : ViewState<T>(result, msg, false)
    class StateLoading<T>(result: T) : ViewState<T>(result, "", true)
}