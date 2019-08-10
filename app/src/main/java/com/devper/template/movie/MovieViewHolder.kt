package com.devper.template.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.devper.template.R
import com.devper.template.movie.model.Movie

class MovieViewHolder(val view: View, private val f: (id: Int, title: String) -> Unit) : RecyclerView.ViewHolder(view) {
    private val titleTextView: TextView = view.findViewById(R.id.titleTextView)
    private val popularityTextView: TextView = view.findViewById(R.id.popularityTextView)
    private val imageView: ImageView = view.findViewById(R.id.imageView)
    private var movie: Movie? = null

    init {
        view.setOnClickListener {
            movie?.let {
                f(it.id, it.title!!)
            }
        }
    }

    fun bind(movie: Movie?, fullImageUrl: String?) {
        movie?.let {
            this.movie = it
            val popularity = getPopularityString(it.popularity)
            popularityTextView.text = popularity
            titleTextView.text = it.title

            Glide.with(view).load(fullImageUrl).run {
                apply(RequestOptions.centerCropTransform())
                transition(DrawableTransitionOptions.withCrossFade())
                into(imageView)
            }
        }
    }

    private fun getPopularityString(popularity: Float): String {
        val decimalFormat = java.text.DecimalFormat("#.#")
        return decimalFormat.format(popularity.toDouble())
    }

    companion object {
        fun create(parent: ViewGroup, f: (d: Int, title: String) -> Unit): MovieViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
            return MovieViewHolder(view, f)
        }
    }

}