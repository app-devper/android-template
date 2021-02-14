package com.devper.template.presentation.userdetail

import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devper.template.AppConfig.EXTRA_FLOW
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.AppConfig.FLOW_UPDATE_USER
import com.devper.template.R
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.user.User
import com.devper.template.domain.usecase.user.GetUserUseCase
import com.devper.template.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel() {

    private var _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> = _user
    val userResult = SingleLiveEvent<ResultState<User>>()

    fun getUser(id: String) {
        getUserUseCase(id)
            .onEach { userResult.value = it }
            .launchIn(viewModelScope)
    }

    fun setUser(user: User) {
        _user.value = user
    }

    fun nextToUpdate() {
        val bundle = bundleOf(
            EXTRA_FLOW to FLOW_UPDATE_USER,
            EXTRA_PARAM to _user.value
        )
        onNavigate(R.id.user_to_user_update, bundle)
    }

}