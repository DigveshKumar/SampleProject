package com.digvesh.sampleproject.presentation.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.digvesh.sampleproject.domain.model.UserInfo
import com.digvesh.sampleproject.presentation.ui.adapter.viewholder.UserListItemViewHolder

class UserListAdapter : RecyclerView.Adapter<UserListItemViewHolder>() {

    lateinit var onClickListener: OnClickListener

    val differ = AsyncListDiffer(this, LIST_COMPARATOR)

    interface OnClickListener {
        fun onClick(userId: Int?)
    }

    companion object {
        private val LIST_COMPARATOR = object : DiffUtil.ItemCallback<UserInfo>() {
            override fun areItemsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        return UserListItemViewHolder.create(parent, onClickListener)
    }

    override fun getItemCount(): Int = differ.currentList.size

}