package com.eltex.shultestable.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.eltex.shultestable.R
import com.eltex.shultestable.databinding.FragmentGraphicsBinding
import com.eltex.shultestable.db.AppDb
import com.eltex.shultestable.model.TrainRecord
import com.eltex.shultestable.repository.SQLiteRecordRepository
import com.eltex.shultestable.utils.LineGraphView
import com.eltex.shultestable.viewmodel.GraphicsViewModel
import kotlinx.coroutines.launch

class FragmentGraphics : Fragment() {

    private var _binding: FragmentGraphicsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<GraphicsViewModel> {
        viewModelFactory {
            initializer {
                GraphicsViewModel(
                    recordRepository = SQLiteRecordRepository(
                        AppDb.getInstance(requireContext().applicationContext).recordDao
                    )
                )
            }
        }
    }

    private lateinit var graphTypeSpinner: Spinner
    private lateinit var graphValuesSpinner: Spinner
    private lateinit var lineGraphView: LineGraphView
    private lateinit var refreshButton: Button
    private lateinit var backButton: ImageButton
    private lateinit var bestResultTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGraphicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        graphTypeSpinner = binding.graphTypeSpinner
        graphValuesSpinner = binding.graphValuesSpinner
        lineGraphView = binding.lineGraphView
        refreshButton = binding.refreshButton
        backButton = binding.backButton
        bestResultTextView = binding.bestResultTextView
        setupSpinners()
        setupRefreshButton()
        setupBackButton()
        loadGraphData()
        loadBestResult()
    }

    private fun setupSpinners() {
        val graphTypesAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.mode_set,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        graphTypeSpinner.adapter = graphTypesAdapter

        val graphValuesAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.level_set,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        graphValuesSpinner.adapter = graphValuesAdapter
    }

    private fun setupRefreshButton() {
        refreshButton.setOnClickListener {
            loadGraphData()
            loadBestResult()
        }
    }

    private fun setupBackButton() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun loadGraphData() {
        lifecycleScope.launch {
            viewModel.getAllRecordsByModeAndLevel(
                graphTypeSpinner.selectedItem.toString(),
                graphValuesSpinner.selectedItem.toString()
            ).collect { records: List<TrainRecord> ->
                val graphData = records.map { record ->
                    Pair(record.id.toFloat(), record.time.toFloat())
                }
                lineGraphView.setData(graphData)
            }
        }
    }

    private fun loadBestResult() {
        lifecycleScope.launch {
            viewModel.getBestResultByModeAndLevel(
                graphTypeSpinner.selectedItem.toString(),
                graphValuesSpinner.selectedItem.toString()
            ).collect { bestRecord ->
                bestResultTextView.text = bestRecord?.let {
                    "Лучшее время: ${it.time}"
                } ?: "Нет записей"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}