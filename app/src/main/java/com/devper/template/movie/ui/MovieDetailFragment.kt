package com.devper.template.movie.ui

import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.getViewModel
import com.devper.template.app.ui.BaseFragment
import com.devper.template.R
import com.devper.template.appCompat
import com.devper.template.databinding.FragmentMovieDetailBinding
import com.devper.template.handlerResponse
import org.koin.androidx.scope.currentScope

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>() {

    override fun getLayout() = R.layout.fragment_movie_detail

    override fun initViewModel() = currentScope.getViewModel<MovieDetailViewModel>(this)

    override fun setupView() {
        val args: MovieDetailFragmentArgs by navArgs()
        binding.viewDetailModel = viewModel
        appCompat().supportActionBar?.show()
        appCompat().supportActionBar?.title = args.title
        viewModel.movieId(args.movieId)
        binding.baseUrl = args.baseUrl
    }

    override fun setObserve() {
        with(viewModel) {
            movieResult.observe(viewLifecycleOwner, Observer {
                val movie = handlerResponse(it)
                movie?.let { item ->
                    Log.i("Detail", it.toString())
                    binding.movie = item
                }
            })
        }
    }
}

