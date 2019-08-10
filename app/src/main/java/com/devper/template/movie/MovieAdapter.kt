package com.devper.template.movie

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.devper.template.R
import com.devper.template.common.util.NetworkState
import com.devper.template.member.NetworkStateItemViewHolder
import com.devper.template.movie.model.Images
import com.devper.template.movie.model.Movie

class MovieAdapter(private val retry: () -> Unit, private val onClick: (id: Int, title: String) -> Unit) : PagedListAdapter<Movie, RecyclerView.ViewHolder>(POST_COMPARATOR) {
    var images: Images? = null
    private var networkState: NetworkState? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_movie -> (holder as MovieViewHolder).bind(getItem(position), getFullImageUrl(getItem(position)!!))
            R.layout.item_network_state -> (holder as NetworkStateItemViewHolder).bindTo(networkState)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_movie -> MovieViewHolder.create(parent, onClick)
            R.layout.item_network_state -> NetworkStateItemViewHolder.create(parent, retry)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.item_movie
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    private fun getFullImageUrl(movie: Movie): String {
        return movie.getFullImageUrl(images!!.baseUrlFull)
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id
        }
    }
}
