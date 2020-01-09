package com.kreactive.test.app.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.kreactive.test.app.data.datasource.ResultWrapper
import com.kreactive.test.app.domain.DataState
import kotlinx.coroutines.Dispatchers

abstract class BaseRepository {

    protected fun <T, A> loadData(
        databaseSource: () -> LiveData<out T>?,
        networkCall: suspend () -> ResultWrapper<A>?,
        saveData: suspend (A) -> Unit
    ): LiveData<DataState<T>> {
        return liveData(Dispatchers.IO) {
            emit(DataState<T>(null, null, DataState.NetworkStatus.LOADING))
            val databaseLiveData = databaseSource.invoke()
            if (databaseLiveData != null) {
                emitSource(
                    databaseLiveData.map {
                        DataState<T>(it, null, DataState.NetworkStatus.SUCCESS)
                    }
                )
            }
            val responseStatus = networkCall.invoke()

            when (responseStatus) {
                is ResultWrapper.Success -> {
                    saveData.invoke(responseStatus.value)
                }
                is ResultWrapper.NetworkError -> {
                    emit(DataState<T>(null, null, DataState.NetworkStatus.ERROR))
                }
            }
        }
    }
}