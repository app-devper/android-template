package com.devper.template.presentation.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devper.template.databinding.ItemNotificationBinding
import com.devper.template.domain.model.notification.Notification

class NotificationViewHolder(private val binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root) {

    var onClick: (notification: Notification) -> Unit = {}

    fun bind(notification: Notification?) {
        notification?.let {
            binding.tvTitle.text = it.title
            binding.tvBody.text = it.body
            binding.tvDate.text = it.displayCreatedDate
        }
    }

    companion object {
        fun create(parent: ViewGroup, onClick: (notification: Notification) -> Unit): NotificationViewHolder {
            val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return NotificationViewHolder(binding).apply {
                this.onClick = onClick
            }
        }
    }

}