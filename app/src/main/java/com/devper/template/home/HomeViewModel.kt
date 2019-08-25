package com.devper.template.home

import androidx.lifecycle.ViewModel
import com.devper.template.movie.data.MovieRepository

class HomeViewModel internal constructor(private val repo: MovieRepository) : ViewModel()
