package com.devper.template.presentation

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devper.template.core.platform.MutableLiveEvent
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.success
import com.devper.template.domain.model.notification.SubscriptionParam
import com.devper.template.domain.model.user.User
import com.devper.template.domain.usecase.auth.HaveLogInUseCase
import com.devper.template.domain.usecase.auth.KeepAliveUseCase
import com.devper.template.domain.usecase.notification.GetUnreadUseCase
import com.devper.template.domain.usecase.notification.SubscriptionUseCase
import com.devper.template.domain.usecase.preference.HavePinUseCase
import com.devper.template.domain.usecase.user.GetProfileUseCase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val keepAliveUseCase: KeepAliveUseCase,
    private val subscriptionUseCase: SubscriptionUseCase,
    private val getUnreadUseCase: GetUnreadUseCase,
    private val haveLogInUseCase: HaveLogInUseCase,
    private val havePinUseCase: HavePinUseCase,
) : ViewModel() {

    private val _profile = MutableLiveData<ResultState<User>>()
    val profileLiveData: LiveData<ResultState<User>> = _profile

    val errorLiveData = MutableLiveEvent<Pair<String, String>?>()

    val loginLiveData = MutableLiveData<Boolean>()

    val badgeLiveData = MutableLiveData<Int>()
    val badge: Int
        get() = badgeLiveData.value ?: -1

    private val _message = SingleLiveEvent<Bundle>()
    val messageLiveData: LiveData<Bundle> = _message

    var resultPin = SingleLiveEvent<Boolean>()

    fun havePin() {
        viewModelScope.launch {
            resultPin.value = havePinUseCase(Unit).success()
        }
    }

    fun getProfile() {
        getProfileUseCase(Unit).onEach {
            _profile.value = it
        }.launchIn(viewModelScope)
    }

    fun keepAlive() {
        viewModelScope.launch {
            keepAliveUseCase(Unit)
        }
    }

    fun initLogin() {
        viewModelScope.launch {
            loginLiveData.value = haveLogInUseCase(Unit).success()
        }
    }

    private fun subscription(deviceToken: String) {
        viewModelScope.launch {
            subscriptionUseCase(SubscriptionParam(deviceToken, "MOBILE"))
        }
    }

    fun subscription() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                it.result?.let { token ->
                    subscription(token)
                    Timber.d("Instance token: $token")
                }
            }
        }
    }

    fun getUnread() {
        getUnreadUseCase(Unit).onEach {
            if (it is ResultState.Success) {
                badgeLiveData.value = it.data
            }
        }.launchIn(viewModelScope)
    }

    fun getUser(): User? {
        return _profile.value?.success()
    }

    fun clearUser() {
        loginLiveData.value = false
        _profile.value = null
    }

    fun isLogin() = loginLiveData.value ?: false

    fun error(code: String, msg: String) {
        errorLiveData.setEventValue(Pair(code, msg))
    }

    fun setMessage(bundle: Bundle?) {
        _message.value = bundle
    }

}
