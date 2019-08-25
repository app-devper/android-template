package com.devper.template.movie.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.devper.common.api.AbsentLiveData
import com.devper.common.api.Resource
import com.devper.template.movie.data.MovieRepository
import com.devper.template.movie.model.Movie

class MovieDetailViewModel internal constructor(private val repo: MovieRepository) : ViewModel() {

    private val movieId: MutableLiveData<Int> = MutableLiveData()
    val movieResult: LiveData<Resource<Movie>> = Transformations.switchMap(movieId) {
        when (it!=null) {
            true -> repo.getMovie(it)
            else -> AbsentLiveData.create()
        }
    }

    fun movieId(id: Int){
        movieId.postValue(id)
    }
}