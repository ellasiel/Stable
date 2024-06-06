package com.eltex.shultestable.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import com.eltex.shultestable.R
import com.eltex.shultestable.databinding.InfoCardBinding
import com.eltex.shultestable.model.TrainRecord

class RecordsAdapter(
    private val listener: EventListener
) : ListAdapter<TrainRecord, RecordViewHolder>(RecordDiffCallback()) {
    interface EventListener {
        fun onDeleteClicked(record: TrainRecord)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = InfoCardBinding.inflate(layoutInflater, parent, false)
        val viewHolder = RecordViewHolder(binding)

        binding.menu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.statistic_menu)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.delete -> {
                            listener.onDeleteClicked(getItem(viewHolder.adapterPosition))
                            true
                        }

                        else -> false
                    }
                }
                show()
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}