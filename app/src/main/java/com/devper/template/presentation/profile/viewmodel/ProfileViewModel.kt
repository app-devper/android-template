package com.devper.template.presentation.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.user.User
import com.devper.template.domain.usecase.user.GetProfileUseCase

class ProfileViewModel internal constructor(private val getProfileUseCase: GetProfileUseCase) :
    ViewModel() {

    private var results: MutableLiveData<ResultState<User>> = MutableLiveData()

    fun getProfile() {
        getProfileUseCase.execute(null) {
            onStart { results.value = ResultState.Loading() }
            onComplete { results.value = ResultState.Success(it) }
            onError { results.value = ResultState.Error(it) }
        }
    }

    val profileLiveDate: LiveData<ResultState<User>> = results

    override fun onCleared() {
        super.onCleared()
        getProfileUseCase.unsubscribe()
    }

}