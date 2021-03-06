package com.devper.template.presentation.userupdate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devper.template.AppConfig.FLOW_UPDATE_PROFILE
import com.devper.template.AppConfig.FLOW_UPDATE_USER
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.user.User
import com.devper.template.domain.model.user.UserUpdateParam
import com.devper.template.domain.usecase.user.UpdateProfileUseCase
import com.devper.template.domain.usecase.user.UpdateUserUseCase
import com.devper.template.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserFormViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : BaseViewModel() {

    var userParam = MutableLiveData(UserUpdateParam())
    val resultUser = SingleLiveEvent<ResultState<User>>()

    fun setUser(user: User) {
        userParam.value = userParam.value?.apply {
            id = user.id
            username = user.username
            firstName = user.firstName ?: ""
            lastName = user.lastName ?: ""
            phone = user.phone ?: ""
            email = user.email ?: ""
        }
    }

    fun update() {
        if (flow == FLOW_UPDATE_PROFILE) {
            updateProfile()
        } else if (flow == FLOW_UPDATE_USER) {
            updateUser()
        }
    }

    private fun updateProfile() {
        userParam.value?.let { param ->
            updateProfileUseCase(param)
                .onEach { resultUser.value = it }
                .launchIn(viewModelScope)
        }
    }

    private fun updateUser() {
        userParam.value?.let { param ->
            updateUserUseCase(param)
                .onEach { resultUser.value = it }
                .launchIn(viewModelScope)
        }
    }
}