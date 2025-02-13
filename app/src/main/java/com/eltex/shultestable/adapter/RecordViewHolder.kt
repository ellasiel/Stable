package com.eltex.shultestable.adapter

import androidx.recyclerview.widget.RecyclerView
import com.eltex.shultestable.databinding.InfoCardBinding
import com.eltex.shultestable.model.TrainRecord

class RecordViewHolder(
    private val binding: InfoCardBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(record: TrainRecord) {
        binding.numberTrain.text = record.id.toString()
        binding.numberTime.text = record.numberTime
        binding.mode.text = "mode:  " + record.mode
        binding.level.text = "level:  " + record.level
        binding.time.text = "time:  " + record.time
        binding.mistakes.text = "mistakes:  " + record.mistakes
    }

}