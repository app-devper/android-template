package com.devper.template.presentation.home

import androidx.lifecycle.ViewModel
import com.devper.template.domain.repository.MovieRepository

class HomeViewModel internal constructor(private val repo: MovieRepository) : ViewModel()
