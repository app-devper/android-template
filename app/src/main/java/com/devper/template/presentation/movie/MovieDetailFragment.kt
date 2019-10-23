package com.devper.template.presentation.movie

import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.devper.template.R
import com.devper.template.databinding.FragmentMovieDetailBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.main.appCompat
import com.devper.template.presentation.main.hideLoading
import com.devper.template.presentation.main.showLoading
import com.devper.template.presentation.main.toError
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(R.layout.fragment_movie_detail) {

    val viewModel: MovieDetailViewModel by viewModel()

    override fun setupView() {
        val args: MovieDetailFragmentArgs by navArgs()
        binding.viewDetailModel = viewModel
        appCompat().supportActionBar?.show()
        appCompat().supportActionBar?.title = args.title
        viewModel.movieId(args.movieId)
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

