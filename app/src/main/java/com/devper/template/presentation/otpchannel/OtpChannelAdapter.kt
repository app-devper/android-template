package com.devper.template.presentation.otpchannel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devper.template.core.platform.ItemDiffUtil
import com.devper.template.databinding.ItemOtpChannelBinding
import com.devper.template.domain.model.otp.Channel

class OtpChannelAdapter : ListAdapter<Channel, OtpChannelAdapter.ChannelViewHolder>(ItemDiffUtil<Channel>()) {

    var onClick: (verifyChannel: Channel) -> Unit = {}

    class ChannelViewHolder(val binding: ItemOtpChannelBinding) : RecyclerView.ViewHolder(binding.root){

        companion object {
            fun create(parent: ViewGroup): ChannelViewHolder {
                val binding = ItemOtpChannelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ChannelViewHolder(binding)
            }
        }
    }

    private var channelSelected = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        return ChannelViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val data = getItem(position)
        data?.let {
            holder.binding.radioChannel.title = it.value
        }

        holder.binding.radioChannel.isCheck = position == channelSelected

        holder.binding.radioChannel.setOnClickListener {
            channelSelected = position
            notifyDataSetChanged()
        }
    }

    fun getSelected(): Channel {
        return getItem(channelSelected)
    }
}