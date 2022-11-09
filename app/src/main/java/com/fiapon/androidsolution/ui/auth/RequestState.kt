/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui.auth

sealed class RequestState<out T> {
    object Loading : RequestState<Nothing>()
    data class Success<T>(val data: T) : RequestState<T>()
    data class Error(val throwable: Throwable) : RequestState<Nothing>()
}