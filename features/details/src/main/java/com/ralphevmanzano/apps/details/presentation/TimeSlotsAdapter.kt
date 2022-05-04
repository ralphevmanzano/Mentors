package com.ralphevmanzano.apps.details.presentation

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ralphevmanzano.apps.details.R
import com.ralphevmanzano.apps.details.databinding.ItemTimeSlotBinding
import com.ralphevmanzano.apps.domain.model.TimeSlot

class TimeSlotsAdapter :
    ListAdapter<TimeSlot, TimeSlotsAdapter.TimeSlotsViewHolder>(TimeSlotDiffCallback()) {

    var onItemClick: ((Pair<Int, Int>) -> Unit)? = null
    var prevSelected = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSlotsViewHolder {
        val binding =
            ItemTimeSlotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeSlotsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeSlotsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TimeSlotsViewHolder(private val binding: ItemTimeSlotBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            onItemClick?.let {
                binding.root.setOnClickListener {
                    it(Pair(prevSelected, adapterPosition))
                }
            }
        }

        fun bind(item: TimeSlot) = with(binding) {
            val context = binding.root.context
            tvTimeSlot.text = item.label
            enableOrDisable(item.isAvailable)
            if (item.isSelected) cardContainer.background =
                ContextCompat.getDrawable(context, R.drawable.bg_selected)
            else {
                if (cardContainer.background != null) cardContainer.background = null
            }
        }

        private fun enableOrDisable(isEnabled: Boolean) = with(binding) {
            cardContainer.isClickable = isEnabled
            cardContainer.isFocusable = isEnabled
            cardContainer.cardElevation = if (isEnabled) 8f else 0f
            cardContainer.background =
                ContextCompat.getDrawable(binding.root.context, R.drawable.bg_disabled)
            if (!isEnabled) tvTimeSlot.setTextColor(Color.parseColor("#cccccc"))
            else tvTimeSlot.setTextColor(Color.parseColor("#000000"))
        }
    }

    class TimeSlotDiffCallback : DiffUtil.ItemCallback<TimeSlot>() {
        override fun areItemsTheSame(oldItem: TimeSlot, newItem: TimeSlot) =
            oldItem.label == newItem.label

        override fun areContentsTheSame(oldItem: TimeSlot, newItem: TimeSlot) =
            oldItem.isSelected == newItem.isSelected && oldItem.isAvailable == newItem.isAvailable
    }
}