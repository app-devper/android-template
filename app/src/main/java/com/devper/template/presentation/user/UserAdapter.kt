package com.devper.template.presentation.user

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devper.template.R
import com.devper.template.core.platform.ItemDiffUtil
import com.devper.template.core.platform.NetworkState
import com.devper.template.core.platform.widget.NetworkStateViewHolder
import com.devper.template.domain.model.user.User

class UserAdapter : PagedListAdapter<User, RecyclerView.ViewHolder>(ItemDiffUtil<User>()) {

    private var networkState: NetworkState? = null

    var onClick: (user: User) -> Unit = {}
    var retry: () -> Unit = {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_movie -> (holder as UserViewHolder).bind(getItem(position))
            R.layout.item_network_state -> (holder as NetworkStateViewHolder).bindTo(networkState)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_movie -> UserViewHolder.create(parent, onClick)
            R.layout.item_network_state -> NetworkStateViewHolder.create(parent, retry)
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

}
