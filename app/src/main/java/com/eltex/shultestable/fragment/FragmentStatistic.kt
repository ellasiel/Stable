package com.eltex.shultestable.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.eltex.shultestable.R
import com.eltex.shultestable.adapter.RecordsAdapter
import com.eltex.shultestable.databinding.FragmentStatisticBinding
import com.eltex.shultestable.db.AppDb
import com.eltex.shultestable.itemdecoration.OffsetDecoration
import com.eltex.shultestable.model.Record
import com.eltex.shultestable.repository.SQLiteRecordRepository
import com.eltex.shultestable.viewmodel.RecordViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
class FragmentStatistic : Fragment() {
    companion object {
        const val EDITED_EVENT_ID = "com.eltex.shultestable.EDITED_EVENT_ID"
    }
    private var editedId = 0L
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreate(savedInstanceState)
        val binding = FragmentStatisticBinding.inflate(inflater, container, false)
        if (savedInstanceState != null) {
            editedId = savedInstanceState.getLong(EDITED_EVENT_ID)
        }
        val viewModel by viewModels<RecordViewModel> {
            viewModelFactory {
                initializer {
                    RecordViewModel(
                        SQLiteRecordRepository(
                            AppDb.getInstance(requireContext().applicationContext).recordDao
                        )
                    )
                }
            }
        }
        val adapter = RecordsAdapter(
            object : RecordsAdapter.EventListener {

                override fun onDeleteClicked(record: Record) {
                }
            }
        )
        binding.list.adapter = adapter
        binding.list.addItemDecoration(
            OffsetDecoration(resources.getDimensionPixelSize(R.dimen.small_spacing))
        )
        viewModel.uiState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                adapter.submitList(it.records)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
        return binding.root
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(EDITED_EVENT_ID, editedId)
    }
}