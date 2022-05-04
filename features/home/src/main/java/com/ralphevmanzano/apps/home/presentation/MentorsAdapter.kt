package com.ralphevmanzano.apps.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ralphevmanzano.apps.domain.model.Mentor
import com.ralphevmanzano.apps.home.databinding.ItemMentorListBinding

class MentorsAdapter :
    ListAdapter<Mentor, MentorsAdapter.MentorsViewHolder>(MentorsDiffCallback()) {

    var onItemClick: ((Mentor) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentorsViewHolder {
        val binding =
            ItemMentorListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MentorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MentorsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MentorsViewHolder(private val binding: ItemMentorListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            onItemClick?.let {
                binding.root.setOnClickListener {
                    it(getItem(adapterPosition))
                }
            }
        }

        fun bind(item: Mentor) = with(binding) {
            Glide.with(binding.root).load(item.avatarUrl).circleCrop().into(ivMentor)
            tvLogin.text = item.login
            tvUrl.text = item.htmlUrl
        }
    }

    class MentorsDiffCallback : DiffUtil.ItemCallback<Mentor>() {
        override fun areItemsTheSame(oldItem: Mentor, newItem: Mentor) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Mentor, newItem: Mentor) = oldItem == newItem
    }
}