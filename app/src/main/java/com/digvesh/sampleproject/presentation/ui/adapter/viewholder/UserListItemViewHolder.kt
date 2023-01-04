package com.digvesh.sampleproject.presentation.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digvesh.core.extensions.loadImage
import com.digvesh.sampleproject.R
import com.digvesh.sampleproject.databinding.UserListItemViewBinding
import com.digvesh.sampleproject.domain.model.UserInfo
import com.digvesh.sampleproject.presentation.ui.adapter.UserListAdapter

class UserListItemViewHolder(
    private val binding: UserListItemViewBinding,
    private val onClickListener: UserListAdapter.OnClickListener
) : RecyclerView.ViewHolder(binding.root) {

    private var user: UserInfo? = null

    fun bind(user: UserInfo?) {
        user?.let {
            updateViews(it)
        }
    }

    private fun updateViews(user: UserInfo) {
        this.user = user
        binding.userName.text = user.displayName
        binding.avatarImage.loadImage(user.avatarImage)
        binding.root.setOnClickListener {
            onClickListener.onClick(user.id)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClickListener: UserListAdapter.OnClickListener
        ): UserListItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_list_item_view, parent, false)
            val binding = UserListItemViewBinding.bind(view)
            return UserListItemViewHolder(binding, onClickListener)
        }
    }
}