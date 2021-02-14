package com.devper.template.presentation.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devper.template.core.platform.ItemDiffUtil
import com.devper.template.databinding.ItemUserBinding
import com.devper.template.domain.model.user.User

class UserPageAdapter : PagingDataAdapter<User, UserPageAdapter.UserViewHolder>(ItemDiffUtil()) {

    var onClick: (user: User) -> Unit = {}

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
            holder.binding.root.setOnClickListener {
                onClick.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.create(parent)
    }

    class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(it: User) {
            binding.tvPopularity.text = it.username
            binding.tvTitle.text = it.email
        }

        companion object {
            fun create(parent: ViewGroup): UserViewHolder {
                val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return UserViewHolder(binding)
            }
        }
    }

}
