package com.devper.template.presentation.user.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.devper.template.core.platform.ItemDiffUtil
import com.devper.template.domain.model.user.User

class UserPageAdapter : PagingDataAdapter<User, UserViewHolder>(ItemDiffUtil()) {

    var onClick: (user: User) -> Unit = {}

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.create(parent, onClick)
    }

}
