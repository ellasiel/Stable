package com.eltex.shultestable.adapter

import androidx.recyclerview.widget.RecyclerView
import com.eltex.shultestable.databinding.InfoCardBinding
import com.eltex.shultestable.model.Record

class RecordViewHolder(
    private val binding: InfoCardBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(record: Record) {
        binding.numberTrain.text = record.id.toString()
        binding.numberTime.text = record.numberTime
        binding.mode.text = record.mode
        binding.level.text = record.level
        binding.time.text = record.time
        binding.mistakes.text = record.mistakes
    }

}