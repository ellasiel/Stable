package com.eltex.shultestable.adapter

import androidx.recyclerview.widget.DiffUtil
import com.eltex.shultestable.model.GameRecord

class RecordDiffCallback : DiffUtil.ItemCallback<GameRecord>() {
    override fun areItemsTheSame(oldItem: GameRecord, newItem: GameRecord): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: GameRecord, newItem: GameRecord): Boolean =
        oldItem == newItem
}