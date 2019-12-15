package com.devper.template.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devper.template.domain.core.ResultState
import com.devper.template.core.model.user.User
import com.devper.template.domain.usecase.user.GetUserUseCase

class ProfileViewModel internal constructor(private val getUserUseCase: GetUserUseCase) : ViewModel() {

    private var results: MutableLiveData<ResultState<User>> = MutableLiveData()

    fun getProfile(id: String) {
        getUserUseCase.execute(id) {
            onStart { results.value = ResultState.Loading() }
            onComplete { results.value = ResultState.Success(it) }
            onError { results.value = ResultState.Error(it) }
        }
    }

    val liveDataProfile: LiveData<ResultState<User>> = results

    override fun onCleared() {
        super.onCleared()
        getUserUseCase.unsubscribe()
    }

}