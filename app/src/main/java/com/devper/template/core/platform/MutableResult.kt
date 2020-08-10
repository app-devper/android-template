package com.devper.template.core.platform

import androidx.lifecycle.MutableLiveData
import com.devper.template.domain.core.ResultState

class MutableResult<T> : MutableLiveData<ResultState<T>>() {

    fun loading() {
        this.value = ResultState.Loading()
    }

    fun success(t: T) {
        this.value = ResultState.Success(t)
    }

    fun error(t: Throwable) {
        this.value = ResultState.Error(t)
    }

}
