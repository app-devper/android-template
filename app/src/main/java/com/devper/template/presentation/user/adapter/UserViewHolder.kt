package com.devper.template.presentation.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devper.template.databinding.ItemUserBinding
import com.devper.template.domain.model.user.User

class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

    var onClick: (user: User) -> Unit = {}

    fun bind(user: User?) {
        user?.let {
            binding.tvPopularity.text = it.username
            binding.tvTitle.text = it.email
            binding.root.setOnClickListener {
                onClick.invoke(user)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, onClick: (user: User) -> Unit): UserViewHolder {
            val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return UserViewHolder(binding).apply {
                this.onClick = onClick
            }
        }
    }

}