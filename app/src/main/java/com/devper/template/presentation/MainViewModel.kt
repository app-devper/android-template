package com.devper.template.presentation

import android.os.Bundle
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devper.template.R
import com.devper.template.core.platform.MutableLiveEvent
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.notification.SubscriptionParam
import com.devper.template.domain.model.user.User
import com.devper.template.domain.usecase.auth.KeepAliveUseCase
import com.devper.template.domain.usecase.notification.GetUnreadUseCase
import com.devper.template.domain.usecase.notification.SubscriptionUseCase
import com.devper.template.domain.usecase.user.GetProfileUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel @ViewModelInject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val keepAliveUseCase: KeepAliveUseCase,
    private val subscriptionUseCase: SubscriptionUseCase,
    private val getUnreadUseCase: GetUnreadUseCase,
) : ViewModel() {

    private val _profile = SingleLiveEvent<ResultState<User>>()
    val profileLiveDate: LiveData<ResultState<User>> = _profile

    private val _user = MutableLiveData<User>()
    val userLiveData: LiveData<User> = _user

    private val _navigate = SingleLiveEvent<Pair<Int, Bundle?>>()
    val navigateLiveData: LiveData<Pair<Int, Bundle?>> = _navigate

    private val _accessToken = MutableLiveData<String>()
    val accessTokenLiveData: LiveData<String> = _accessToken

    val resultLogin = SingleLiveEvent<ResultState<String>>()
    val resultSubscription = SingleLiveEvent<ResultState<Unit>>()

    val errorLiveData = MutableLiveEvent<Pair<String, String>>()

    val codeLiveData = MutableLiveEvent<String>()

    val badgeLiveData = MutableLiveData<String>()
    val badge: String
        get() = badgeLiveData.value ?: ""

    private val _message = SingleLiveEvent<Bundle>()
    val messageLiveData: LiveData<Bundle> = _message

    fun getProfile() {
        if (_profile.value != null) {
            return
        }
        getProfileUseCase(Unit).onEach {
            _profile.value = it
        }.launchIn(viewModelScope)
    }

    fun keepAlive() {
        keepAliveUseCase(Unit).onEach {
            resultLogin.value = it
        }.launchIn(viewModelScope)
    }

    fun subscription(deviceToken: String) {
        if (resultSubscription.value != null) {
            return
        }
        subscriptionUseCase(SubscriptionParam(deviceToken, "MOBILE")).onEach {
            resultSubscription.value = it
        }.launchIn(viewModelScope)
    }

    fun getUnread() {
        getUnreadUseCase(Unit).onEach {
            if (it is ResultState.Success) {
                badgeLiveData.value = it.data.toString()
            }
        }.launchIn(viewModelScope)
    }

    fun getUser(): User? {
        return _user.value
    }

    fun setUser(user: User) {
        _user.value = user
    }

    fun setAccessToken(accessToken: String?) {
        _accessToken.value = accessToken
    }

    fun clearAccessToken() {
        _accessToken.value = null
    }

    fun isLogin(): Boolean {
        return _accessToken.value != null
    }

    fun error(code: String, msg: String) {
        if (code == "CM-401-112") {
            navigate(R.id.action_to_pin_max_attempt)
        } else {
            errorLiveData.setEventValue(Pair(code, msg))
        }
    }

    fun setMessage(bundle: Bundle?) {
        _message.value = bundle
    }

    fun navigate(id: Int, bundle: Bundle? = null) {
        _navigate.value = Pair(id, bundle)
    }

}
