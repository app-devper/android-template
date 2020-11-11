package com.devper.template.presentation.splash

import androidx.core.os.bundleOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.R
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.usecase.device.RegisterDeviceUseCase
import com.devper.template.domain.usecase.preference.GetUserIdUseCase
import com.devper.template.presentation.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SplashViewModel @ViewModelInject constructor(
    private val registerDeviceUseCase: RegisterDeviceUseCase,
    private val getUserIdUseCase: GetUserIdUseCase
) : BaseViewModel() {

    val username = MutableLiveData<String>()
    private val _result: SingleLiveEvent<ResultState<String>> = SingleLiveEvent()
    val resultLiveData: LiveData<ResultState<String>> = _result

    fun registerDevice() {
        registerDeviceUseCase(Unit)
            .onEach { _result.value = it }
            .launchIn(viewModelScope)
    }

    fun checkLogin(){
        viewModelScope.launch {
            getUserIdUseCase(Unit).let {
                when(it){
                    is ResultState.Success -> {
                        nextPage(it.data)
                    }
                }
            }
        }
    }

    private fun nextPage(it: String) {
        if (it.isNotEmpty()) {
            val bundle = bundleOf(EXTRA_PARAM to it)
            onNavigate(R.id.splash_to_pin_code, bundle)
        } else {
            onNavigate(R.id.splash_to_login, null)
        }
    }

}
