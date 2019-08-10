package com.devper.template.movie

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.devper.common.createCallService
import com.devper.template.common.MOVIE_URL
import com.devper.template.home.HomeViewModel
import com.devper.template.moviedetail.MovieDetailRepository
import com.devper.template.moviedetail.MovieDetailViewModel

val movieModule = module {
    single { createCallService<MovieService>(get(), MOVIE_URL) }
    single { MovieRepository(get(), get()) }
    single { MovieDetailRepository(get(), get()) }
    viewModel { MovieViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
}