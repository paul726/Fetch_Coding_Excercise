package com.pjiang.fetch_coding_exercise.https

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

sealed class ApiResult<T>(val data: T?, val message:String?) {

    data class Success<T>(val _data: T?): ApiResult<T>(
        data = _data,
        message = null
    )

    data class Failed<T>(val errorMsg: String?): ApiResult<T> (data = null, message = errorMsg)
}

fun <T> toFlow(call: suspend () -> Response<T>): Flow<ApiResult<T>> {
    return flow {
        val response = call()
        if (response.isSuccessful) {
            emit(ApiResult.Success(response.body()))
        } else {
            emit(ApiResult.Failed(response.message()))
        }
    }.flowOn(Dispatchers.IO)
}