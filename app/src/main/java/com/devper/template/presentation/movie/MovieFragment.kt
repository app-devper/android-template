package com.devper.template.presentation.movie

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.devper.template.R
import com.devper.template.databinding.FragmentMovieBinding
import com.devper.template.domain.model.movie.Movie
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.movie.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : BaseFragment<FragmentMovieBinding>(R.layout.fragment_movie) {

    private lateinit var adapter: MovieAdapter

    private val movieViewModel: MovieViewModel by viewModel()

    override fun setupView() {
        binding.viewModel = movieViewModel
        adapter = MovieAdapter(this::retry)
        adapter.apply {
            onClick = {
                nextToMovieDetail(it)
            }
        }
        binding.rvMovie.adapter = adapter
    }

    override fun observeLiveData() {
        with(movieViewModel) {
            movieList.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })

            networkState.observe(viewLifecycleOwner, Observer {
                adapter.setNetworkState(it)
            })
        }
    }

    private fun retry() {}

    private fun nextToMovieDetail(movie: Movie) {
        mainViewModel.setMovie(movie)
        findNavController().navigate(R.id.movie_detail_action)
    }
}
