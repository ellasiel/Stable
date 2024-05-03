package com.eltex.shultestable.adapter

import androidx.recyclerview.widget.DiffUtil
import com.eltex.shultestable.model.Record

class RecordDiffCallback : DiffUtil.ItemCallback<Record>() {
    override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean = oldItem == newItem
}