package com.devper.template.movie.ui

import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.devper.template.R
import com.devper.template.app.ui.BaseFragment
import com.devper.template.databinding.FragmentMovieBinding
import com.devper.template.handlerResponse
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MovieFragment : BaseFragment<FragmentMovieBinding, MovieViewModel>() {

    private lateinit var adapter: MovieAdapter

    private var baseUrl = ""

    override fun getLayout() = R.layout.fragment_movie

    override fun initViewModel() = currentScope.getViewModel<MovieViewModel>(this)

    override fun setupView() {
        binding.viewModel = viewModel
        adapter = MovieAdapter(this::retry) { id: Int, title: String ->
            val directions = MovieFragmentDirections.movieDetailAction(id, title, baseUrl)
            findNavController().navigate(directions)
        }
        binding.rvMovie.adapter = adapter
        viewModel.getConfiguration()
    }

    override fun setObserve() {
        with(viewModel) {
            configResult.observe(viewLifecycleOwner, Observer {
                val config = handlerResponse(it)
                config?.let { item ->
                    item.images?.let { images ->
                        adapter.images = images
                        viewModel.getMovies()
                        baseUrl = images.baseUrlFull
                    }
                }
            })

            movieList.observe(viewLifecycleOwner, Observer {
                Log.i("MovieList", "MovieList: " + it.size)
                adapter.submitList(it)
            })

            networkState.observe(viewLifecycleOwner, Observer {
                adapter.setNetworkState(it)
            })
        }
    }

    private fun retry() {

    }

}
