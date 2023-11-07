package com.cdg.cdg_news.model

sealed class RemoteResource<T> {

     data object Loading : RemoteResource<Nothing>()

    data class Success<T>(val data: T) : RemoteResource<T>()

    data class Failure(val errorCode: Int? = null, val errorMessage: String? = null) :
        RemoteResource<Nothing>()
}