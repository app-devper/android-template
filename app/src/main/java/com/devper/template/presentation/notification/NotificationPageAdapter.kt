package com.devper.template.presentation.notification

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.devper.template.core.platform.ItemDiffUtil
import com.devper.template.domain.model.notification.Notification

class NotificationPageAdapter : PagingDataAdapter<Notification, NotificationViewHolder>(ItemDiffUtil()) {

    var onClick: ( notification:  Notification) -> Unit = {}

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder.create(parent, onClick)
    }

}
