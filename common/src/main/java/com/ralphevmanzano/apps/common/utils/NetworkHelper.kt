package com.ralphevmanzano.apps.common.utils

import com.ralphevmanzano.apps.domain.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import timber.log.Timber

suspend fun <T> resultFlow(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> Response<T>
): Flow<Resource<T>> {
    return flow {
        try {
            emit(Resource.loading())
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                emit(Resource.error(response.message(), response.code().toString()))
            }
        } catch (t: Throwable) {
            emit(Resource.error("An unexpected error occurred"))
            Timber.e(t.localizedMessage)
        }
    }.flowOn(dispatcher)
}