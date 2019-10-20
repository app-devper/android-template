package com.devper.template.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.movie.Movie
import com.devper.template.domain.model.user.User
import com.devper.template.domain.usecase.device.RegisterDeviceUseCase
import com.devper.template.domain.usecase.user.GetCurrentUserUseCase

class MainViewModel(
    private val getCurrentUseCase: GetCurrentUserUseCase,
    private val registerDeviceUseCase: RegisterDeviceUseCase
) : ViewModel() {

    val result: MutableLiveData<ResultState<String>> = MutableLiveData()
    var user: MutableLiveData<User> = MutableLiveData()
    var badge: MutableLiveData<String> = MutableLiveData()

    private fun getCurrentUser() {
        getCurrentUseCase.execute("") {
            onComplete { user.value = it }
            onError {
                it.printStackTrace()
                user.value = null
            }
        }
    }

    fun registerDevice() {
        registerDeviceUseCase.execute(""){
            onStart { result.value = ResultState.Loading() }
            onComplete {
                result.value = ResultState.Success(it)
                getCurrentUser()
            }
            onError { result.value = ResultState.Error(it) }
        }
    }

    fun getUser(): User? {
        return user.value
    }

    override fun onCleared() {
        super.onCleared()
        getCurrentUseCase.unsubscribe()
        registerDeviceUseCase.unsubscribe()
    }
}
