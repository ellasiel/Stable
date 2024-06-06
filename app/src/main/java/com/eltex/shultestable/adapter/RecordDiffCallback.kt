package com.eltex.shultestable.adapter

import androidx.recyclerview.widget.DiffUtil
import com.eltex.shultestable.model.TrainRecord

class RecordDiffCallback : DiffUtil.ItemCallback<TrainRecord>() {
    override fun areItemsTheSame(oldItem: TrainRecord, newItem: TrainRecord): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TrainRecord, newItem: TrainRecord): Boolean =
        oldItem == newItem
}