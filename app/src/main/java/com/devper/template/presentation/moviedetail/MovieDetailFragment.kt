package com.devper.template.presentation.moviedetail

import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.databinding.FragmentMovieDetailBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.movie.Movie
import com.devper.template.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(R.layout.fragment_movie_detail) {

    override val viewModel: MovieDetailViewModel by viewModel()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
        binding.viewDetailModel = viewModel
    }

    override fun observeLiveData() {
        viewModel.movieResult.observe(viewLifecycleOwner, Observer {
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

    private fun setMovie(it: Movie) {
        setTitle(it.title ?: "")
        viewModel.movieId(it.id)
    }
}

