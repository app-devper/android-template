package com.devper.template.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devper.template.R
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.usecase.device.RegisterDeviceUseCase
import com.devper.template.presentation.BaseViewModel

class SplashViewModel(
    private val registerDeviceUseCase: RegisterDeviceUseCase
) : BaseViewModel() {

    private val _result: MutableLiveData<ResultState<Boolean>> = MutableLiveData()
    val resultLiveData: LiveData<ResultState<Boolean>> = _result

    fun registerDevice() {
        registerDeviceUseCase.execute(null) {
            onStart { _result.value = ResultState.Loading() }
            onComplete {
                _result.value = ResultState.Success(it)
            }
            onError {
                _result.value = ResultState.Error(it)
            }
        }
    }

    fun nextPage(isSetPin: Boolean) {
        if (isSetPin) {
            onNavigate(R.id.splash_to_pin_code, null)
        } else {
            onNavigate(R.id.splash_to_login, null)
        }
    }

    override fun onCleared() {
        super.onCleared()
        registerDeviceUseCase.unsubscribe()
    }
}
