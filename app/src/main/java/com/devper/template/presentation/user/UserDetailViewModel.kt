package com.devper.template.presentation.user

import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devper.template.AppConfig
import com.devper.template.AppConfig.EXTRA_FLOW
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.AppConfig.FLOW_UPDATE_USER
import com.devper.template.R
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.movie.Movie
import com.devper.template.domain.model.user.User
import com.devper.template.domain.usecase.movie.GetMovieUseCase
import com.devper.template.domain.usecase.user.GetUserUseCase
import com.devper.template.domain.usecase.user.GetUsersUseCase
import com.devper.template.presentation.BaseViewModel

class UserDetailViewModel(private val useCase: GetUserUseCase) : BaseViewModel() {

    private var _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> = _user
    val userResult = SingleLiveEvent<ResultState<User>>()

    fun getUser(id: String) {
        useCase.execute(id) {
            onStart { userResult.value = ResultState.Loading() }
            onComplete { userResult.value = ResultState.Success(it) }
            onError { userResult.value = ResultState.Error(it) }
        }
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

    override fun onCleared() {
        super.onCleared()
        useCase.unsubscribe()
    }
}