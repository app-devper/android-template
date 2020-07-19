package com.devper.template.presentation.main

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devper.template.core.platform.MutableResult
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.user.User
import com.devper.template.domain.usecase.user.GetProfileUseCase

class MainViewModel(
    private val getProfileUseCase: GetProfileUseCase
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

    fun getProfile() {
        getProfileUseCase.execute(null) {
            onStart { results.value = ResultState.Loading() }
            onComplete { results.value = ResultState.Success(it) }
            onError { results.value = ResultState.Error(it) }
        }
    }

    fun initProfile() {
        getProfile()
    }

    fun getUser(): User? {
        return _user.value
    }

    fun setUser(user: User) {
        _user.value = user
    }

    fun setAccessToken(accessToken: String) {
        _accessToken.value = accessToken
    }

    fun navigate(id: Int, bundle: Bundle? = null) {
        _navigate.value = Pair(id, bundle)
    }

    override fun onCleared() {
        super.onCleared()
        getProfileUseCase.unsubscribe()
    }
}
