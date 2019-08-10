package com.devper.common.api

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.devper.common.mainThread

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread constructor() {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        val dbSource = this.loadFromDb()
        result.addSource(dbSource) { resultType ->
            result.removeSource(dbSource)
            if (shouldFetch(resultType)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { rT ->
                    result.value = Resource.success(rT)
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()

        result.addSource(dbSource) { resultType ->
            result.value = Resource.loading(resultType)
        }

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            if (response!!.isSuccessful) {
                processResponse(response)?.let { saveCallResult(it) }
                mainThread {
                    val dbData = loadFromDb()
                    result.addSource(dbData) { resultType ->
                        result.removeSource(dbData)
                        result.value = Resource.success(resultType)
                    }
                }
            } else {
                result.addSource(dbSource) { resultType ->
                    result.removeSource(dbSource)
                    result.value = Resource.error(response.status, response.errorMessage ?: "Unknown error", resultType)
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    @WorkerThread
    protected fun processResponse(response: ApiResponse<RequestType>): RequestType? {
        return response.body
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

}