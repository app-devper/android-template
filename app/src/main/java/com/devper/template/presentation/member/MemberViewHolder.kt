package com.devper.template.presentation.member

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devper.template.R
import com.devper.template.domain.model.member.Member

class MemberViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.title)
    private val subtitle: TextView = view.findViewById(R.id.subtitle)
    private val score: TextView = view.findViewById(R.id.score)
    private val thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    private var member: Member? = null

    init {
        view.setOnClickListener {}
    }

    fun bind(member: Member?) {
        this.member = member
        title.text = member?.firstName
        subtitle.text = member?.lastName
        //Glide.with(view).load(member?.profilePic).into(thumbnail)
    }

    companion object {
        fun create(parent: ViewGroup): MemberViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
            return MemberViewHolder(view)
        }
    }

}