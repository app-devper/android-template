package com.devper.template.presentation.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    val userResult: MutableLiveData<ResultState<User>> = MutableLiveData()

    fun getUser(id: String){
        useCase.execute(id) {
            onStart { userResult.value = ResultState.Loading() }
            onComplete { userResult.value = ResultState.Success(it) }
            onError { userResult.value = ResultState.Error(it) }
        }
    }

    fun setUser(user: User) {
        _user.value = user
    }

    override fun onCleared() {
        super.onCleared()
        useCase.unsubscribe()
    }
}