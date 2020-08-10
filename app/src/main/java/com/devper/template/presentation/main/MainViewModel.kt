package com.devper.template.presentation.main

import android.os.Bundle
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devper.template.core.platform.MutableLiveEvent
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ErrorMapper
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.user.User
import com.devper.template.domain.usecase.auth.KeepAliveUseCase
import com.devper.template.domain.usecase.user.GetProfileUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val keepAliveUseCase: KeepAliveUseCase
) : ViewModel() {
    private var results = SingleLiveEvent<ResultState<User>>()
    private var _user = MutableLiveData<User>()
    val userLiveData: LiveData<User> = _user
    val profileLiveDate: LiveData<ResultState<User>> = results

    private var _navigate = SingleLiveEvent<Pair<Int, Bundle?>>()
    val navigateLiveData: LiveData<Pair<Int, Bundle?>> = _navigate

    var badge = MutableLiveData<String>()

    private var _accessToken = MutableLiveData<String>()
    val accessTokenLiveData: LiveData<String> = _accessToken

    val resultLogin = SingleLiveEvent<ResultState<String>>()

    val errorLiveData = MutableLiveEvent<Pair<String, String>>()

    val codeLiveData = MutableLiveEvent<String>()

    fun getProfile() {
        viewModelScope.launch {
            getProfileUseCase(Unit).collect {
                results.value= it
            }
        }
    }

    fun keepAlive() {
        viewModelScope.launch {
            keepAliveUseCase(Unit).collect{
                resultLogin.value = it
            }
        }
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
        errorLiveData.setEventValue(Pair(code, msg))
    }

    fun error(throwable: Throwable) {
        val appError = ErrorMapper.toAppError(throwable)
        errorLiveData.setEventValue(Pair(appError.resultCode, appError.getDesc()))
    }

    fun navigate(id: Int, bundle: Bundle? = null) {
        _navigate.value = Pair(id, bundle)
    }

}
