package com.eltex.shultestable.fragment

import android.os.Bundle
import android.os.SystemClock
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eltex.shultestable.R
import com.eltex.shultestable.databinding.FragmentSpeedTrainBinding
import com.eltex.shultestable.db.AppDb
import com.eltex.shultestable.model.TrainRecord
import com.eltex.shultestable.repository.SQLiteRecordRepository
import com.eltex.shultestable.utils.DateTimeUtils
import com.eltex.shultestable.viewmodel.SpeedTrainViewModel

class FragmentSpeedTrain : Fragment() {
    private lateinit var binding: FragmentSpeedTrainBinding
    private val args: FragmentSpeedTrainArgs by navArgs()
    private val viewModel by viewModels<SpeedTrainViewModel> {
        viewModelFactory {
            initializer {
                SpeedTrainViewModel(
                    recordRepository = SQLiteRecordRepository(
                        AppDb.getInstance(requireContext().applicationContext).recordDao
                    )
                )
            }
        }
    }

    private var newRecordId: Long = 0L
    private lateinit var numberTime: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpeedTrainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newRecordId = viewModel.getLastRecordId() + 1L
        numberTime = DateTimeUtils.getCurrentDateTime()
        setupGameLevel(args.level)
        setupButtons()
        setupObservers()
        viewModel.startGame()
    }

    private fun setupObservers() {
        viewModel.actualNumber.observe(viewLifecycleOwner) { actualNumber ->
            binding.actualNumber.text = actualNumber.toString()
        }

        viewModel.mistakesCount.observe(viewLifecycleOwner) { count ->
            binding.mistakesCount.text = count.toString()
        }
    }

    private fun setupGameLevel(level: String) {
        when (level) {
            "easy" -> setupGameTable(3, 3)
            "normal" -> setupGameTable(4, 4)
            "hard" -> setupGameTable(5, 5)
        }
    }

    private fun setupButtons() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.endButton.setOnClickListener {
            findNavController().navigate(FragmentSpeedTrainDirections.speedTrainToSpeed())
        }
    }

    private fun setupGameTable(gameColumns: Int, gameRows: Int) {
        binding.speedgameTable.apply {
            columnCount = gameColumns
            rowCount = gameRows
        }
        val allNumbers = viewModel.fillNumbers(gameColumns * gameRows)

        for (i in 0 until gameRows) {
            for (j in 0 until gameColumns) {
                val randomNumber = allNumbers.random()
                val numberTv = createTextView(randomNumber)
                binding.speedgameTable.addView(
                    numberTv,
                    GridLayout.LayoutParams(GridLayout.spec(i, 1f), GridLayout.spec(j, 1f))
                )
                allNumbers.remove(randomNumber)
                numberTv.setOnClickListener {
                    val actualNumber = binding.actualNumber.text.toString().toInt()
                    if (randomNumber == gameColumns * gameRows && randomNumber == actualNumber) {
                        binding.resultTime.stop()
                        viewModel.saveResultTime(
                            TrainRecord(
                                newRecordId,
                                numberTime,
                                mode = "speed",
                                args.level,
                                time = ((SystemClock.elapsedRealtime() - binding.resultTime.base) / 1000.0).toString(),
                                mistakes = binding.mistakesCount.text.toString()
                            )
                        )
                        showEndGame()
                    }
                    viewModel.checkNumber(randomNumber, gameColumns * gameRows)
                }
            }
        }
        binding.resultTime.start()
    }

    private fun showEndGame() {
        binding.endButton.isVisible = true
        binding.resultView.isVisible = true
        binding.resultView.text = binding.resultTime.text
    }

    private fun createTextView(number: Int): TextView {
        return TextView(requireContext()).apply {
            text = number.toString()
            setBackgroundResource(R.drawable.rectangle_background)
            textSize = 24f
            gravity = Gravity.CENTER
            setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }
    }


}