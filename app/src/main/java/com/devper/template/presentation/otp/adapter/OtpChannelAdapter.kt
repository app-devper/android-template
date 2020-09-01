package com.devper.template.presentation.otp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devper.template.R
import com.devper.template.core.platform.ItemDiffUtil
import com.devper.template.domain.model.otp.Channel
import kotlinx.android.synthetic.main.item_otp_channel.view.*

class OtpChannelAdapter : ListAdapter<Channel, OtpChannelAdapter.ChannelViewHolder>(ItemDiffUtil<Channel>()) {

    var onClick: (verifyChannel: Channel) -> Unit = {}

    class ChannelViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private var channelSelected = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        return ChannelViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_otp_channel, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val data = getItem(position)
        data?.let {
            holder.itemView.radioChannel.title = it.value
        }

        holder.itemView.radioChannel.isCheck = position == channelSelected

        holder.itemView.radioChannel.setOnClickListener {
            channelSelected = position
            notifyDataSetChanged()
        }
    }

    fun getSelected(): Channel {
        return getItem(channelSelected)
    }
}