package com.devper.template.presentation.user

import androidx.lifecycle.MutableLiveData
import com.devper.template.AppConfig.FLOW_UPDATE_PROFILE
import com.devper.template.AppConfig.FLOW_UPDATE_USER
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.user.User
import com.devper.template.domain.model.user.UserUpdateParam
import com.devper.template.domain.usecase.user.UpdateProfileUseCase
import com.devper.template.domain.usecase.user.UpdateUserUseCase
import com.devper.template.presentation.BaseViewModel

class UserFormViewModel(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : BaseViewModel() {

    var userParam = MutableLiveData<UserUpdateParam>(UserUpdateParam())
    val userResult = SingleLiveEvent<ResultState<User>>()

    fun setUser(user: User) {
        userParam.value = userParam.value?.apply {
            id = user.id
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
            updateProfileUseCase.execute(param) {
                onStart { userResult.value = ResultState.Loading() }
                onComplete { userResult.value = ResultState.Success(it) }
                onError { userResult.value = ResultState.Error(it) }
            }
        }
    }

    private fun updateUser() {
        userParam.value?.let { param ->
            updateUserUseCase.execute(param) {
                onStart { userResult.value = ResultState.Loading() }
                onComplete { userResult.value = ResultState.Success(it) }
                onError { userResult.value = ResultState.Error(it) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        updateProfileUseCase.unsubscribe()
        updateUserUseCase.unsubscribe()
    }
}