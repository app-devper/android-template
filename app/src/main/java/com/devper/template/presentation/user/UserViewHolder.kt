package com.devper.template.presentation.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devper.template.R
import com.devper.template.domain.model.user.User

class UserViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val titleTextView: TextView = view.findViewById(R.id.titleTextView)
    private val popularityTextView: TextView = view.findViewById(R.id.popularityTextView)

    var onClick: (user: User) -> Unit = {}

    fun bind(user: User?) {
        user?.let {
            popularityTextView.text = it.username
            titleTextView.text = it.email
            view.setOnClickListener {
                onClick.invoke(user)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, onClick: (user: User) -> Unit): UserViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
            return UserViewHolder(view).apply {
                this.onClick = {
                    onClick(it)
                }
            }
        }
    }

}