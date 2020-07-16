package com.devper.template.presentation.movie

import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.databinding.FragmentMovieBinding
import com.devper.template.domain.model.movie.Movie
import com.devper.template.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : BaseFragment<FragmentMovieBinding>(R.layout.fragment_movie) {

    private var adapter: MovieAdapter = MovieAdapter()

    override val viewModel: MovieViewModel by viewModel()

    override fun setupView() {
        showToolbar()
        showBottomNavigation()
        binding.viewModel = viewModel
        adapter.apply {
            onClick = {
                nextToMovieDetail(it)
            }
            retry = {
            }
        }
        binding.rvMovie.adapter = adapter
        viewModel.getMovies()
    }

    override fun observeLiveData() {
        with(viewModel) {
            movieList.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })

            networkState.observe(viewLifecycleOwner, Observer {
                adapter.setNetworkState(it)
            })
        }
    }

    private fun nextToMovieDetail(movie: Movie) {
        mainViewModel.navigate(R.id.movie_to_movie_detail)
    }
}
