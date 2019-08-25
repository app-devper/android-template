package com.devper.template.movie.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.devper.template.app.util.NetworkState
import com.devper.template.app.api.MovieService
import com.devper.template.movie.model.Movie

class MovieDataSourceFactory(apiKey: String, api: MovieService) : DataSource.Factory<Int, Movie>() {

    private var pagedListDataSource = MovieDataSource(apiKey, api)

    val isInitialLoading: LiveData<Boolean> = pagedListDataSource.isInitialLoading

    val networkState: LiveData<NetworkState> = pagedListDataSource.networkState

    override fun create(): DataSource<Int, Movie> = pagedListDataSource

}