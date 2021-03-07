package com.devper.template.presentation.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devper.template.R
import com.devper.template.databinding.ItemNotificationBinding
import com.devper.template.domain.model.notification.Notification

class NotificationViewHolder(val binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(notification: Notification?) {
        notification?.let {
            binding.item = it
            binding.imgNoti.setImageResource(if (it.status == "READ") R.drawable.ic_noti_unactive else R.drawable.ic_noti_active)
        }
    }

    companion object {
        fun create(parent: ViewGroup): NotificationViewHolder {
            val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return NotificationViewHolder(binding)
        }
    }

}