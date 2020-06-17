package com.devper.template.presentation.movie

import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.databinding.FragmentMovieDetailBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.main.appCompat
import com.devper.template.presentation.main.hideLoading
import com.devper.template.presentation.main.showLoading
import com.devper.template.presentation.main.toError
import com.devper.template.presentation.movie.viewmodel.MovieDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(R.layout.fragment_movie_detail) {

    val viewModel: MovieDetailViewModel by viewModel()

    override fun setupView() {
        binding.viewDetailModel = viewModel
        appCompat().supportActionBar?.show()
        appCompat().supportActionBar?.title = ""
        viewModel.movieId(1)
    }

    override fun setObserve() {
        with(viewModel) {
            movieResult.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is ResultState.Loading -> {
                        showLoading()
                    }
                    is ResultState.Success -> {
                        hideLoading()
                        binding.movie = it.data
                    }
                    is ResultState.Error -> {
                        hideLoading()
                        toError(it.throwable)
                    }
                }
            })
        }
    }
}

