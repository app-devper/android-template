package com.devper.template.presentation.home

import androidx.core.os.bundleOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devper.template.AppConfig
import com.devper.template.R
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.success
import com.devper.template.domain.model.auth.LoginPinParam
import com.devper.template.domain.model.auth.Verify
import com.devper.template.domain.model.user.User
import com.devper.template.domain.usecase.auth.LoginPinUseCase
import com.devper.template.domain.usecase.preference.GetUserIdUseCase
import com.devper.template.domain.usecase.preference.HavePinUseCase
import com.devper.template.presentation.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val havePinUseCase: HavePinUseCase,
) : BaseViewModel() {

    var resultPin = SingleLiveEvent<Boolean>()

    fun havePin() {
//        viewModelScope.launch {
//            resultPin.value = havePinUseCase(Unit).success()
//        }
    }

    fun nextToPage(user: User?) {
        user?.let {
            if (it.pin.isNullOrEmpty()) {
                val bundle = bundleOf(
                    AppConfig.EXTRA_FLOW to AppConfig.FLOW_SET_PIN
                )
                onNavigate(R.id.action_to_set_pin, bundle)
            } else {
                val bundle = bundleOf(
                    AppConfig.EXTRA_FLOW to AppConfig.FLOW_VERIFY_PIN
                )
                onNavigate(R.id.action_to_verify_pin, bundle)
            }
        }
    }

}