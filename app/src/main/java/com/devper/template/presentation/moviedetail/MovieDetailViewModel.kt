package com.devper.template.presentation.moviedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.movie.Movie
import com.devper.template.domain.usecase.movie.GetMovieUseCase
import com.devper.template.presentation.BaseViewModel

class MovieDetailViewModel(private val useCase: GetMovieUseCase) : BaseViewModel() {

    val movieResult: MutableLiveData<ResultState<Movie>> = MutableLiveData()

    fun movieId(id: Int){
        useCase.execute(id) {
            onStart { movieResult.value = ResultState.Loading() }
            onComplete { movieResult.value = ResultState.Success(it) }
            onError { movieResult.value = ResultState.Error(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        useCase.unsubscribe()
    }
}