package com.devper.template.movie

import com.devper.template.movie.data.MovieRepository
import com.devper.template.movie.ui.MovieDetailFragment
import com.devper.template.movie.ui.MovieDetailViewModel
import com.devper.template.movie.ui.MovieFragment
import com.devper.template.movie.ui.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val movieModule = module {

    factory { MovieRepository(get(), get()) }

    scope(named<MovieFragment>()) {
        viewModel { MovieViewModel(get()) }
    }

    scope(named<MovieDetailFragment>()) {
        viewModel { MovieDetailViewModel(get()) }
    }

}