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
import com.eltex.shultestable.databinding.FragmentMemoryTrainBinding
import com.eltex.shultestable.db.AppDb
import com.eltex.shultestable.model.TrainRecord
import com.eltex.shultestable.repository.SQLiteRecordRepository
import com.eltex.shultestable.utils.DateTimeUtils
import com.eltex.shultestable.viewmodel.MemoryTrainViewModel

class FragmentMemoryTrain : Fragment() {
    private lateinit var binding: FragmentMemoryTrainBinding
    private val args: FragmentMemoryTrainArgs by navArgs()
    private val viewModel by viewModels<MemoryTrainViewModel> {
        viewModelFactory {
            initializer {
                MemoryTrainViewModel(
                    recordRepository = SQLiteRecordRepository(
                        AppDb.getInstance(requireContext().applicationContext).recordDao
                    )
                )
            }
        }
    }

    private var newRecordId: Long = 0L
    private lateinit var numberTime: String
    private var isClickable = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemoryTrainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newRecordId = viewModel.getLastRecordId() + 1L
        numberTime = DateTimeUtils.getCurrentDateTime()
        setupGameLevel(args.level)
        setupButtons()
        setupObservers()
        viewModel.startTrain()
    }

    private fun setupObservers() {
        viewModel.currentNumber.observe(viewLifecycleOwner) { actualNumber ->
            binding.currentNumber.text = actualNumber.toString()
        }

        viewModel.mistakes2Count.observe(viewLifecycleOwner) { count ->
            binding.mistakes2Count.text = count.toString()
        }

        viewModel.shouldHideNumbers.observe(viewLifecycleOwner) { shouldHide ->
            if (shouldHide) {
                hideNumbers()
                enableClickAndStartTimerAfterDelay(0)
            }
        }
    }

    private fun enableClickAndStartTimerAfterDelay(delayMillis: Long) {
        view?.postDelayed({
            isClickable = true
            binding.resultTime.base = SystemClock.elapsedRealtime()
            binding.resultTime.start()
        }, delayMillis)
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
            findNavController().navigate(FragmentMemoryTrainDirections.memoryTrainToMemory())
        }
    }

    private fun setupGameTable(gameColumns: Int, gameRows: Int) {
        binding.memoryGameTable.apply {
            columnCount = gameColumns
            rowCount = gameRows
        }
        val allNumbers = viewModel.fillNumbers(gameColumns * gameRows)

        for (i in 0 until gameRows) {
            for (j in 0 until gameColumns) {
                val randomNumber = allNumbers.random()
                val numberTv = createTextView(randomNumber)
                binding.memoryGameTable.addView(
                    numberTv,
                    GridLayout.LayoutParams(GridLayout.spec(i, 1f), GridLayout.spec(j, 1f))
                )
                allNumbers.remove(randomNumber)
                numberTv.setOnClickListener {
                    if (isClickable) {
                        val actualNumber = binding.currentNumber.text.toString().toInt()
                        if (randomNumber == gameColumns * gameRows && randomNumber == actualNumber) {
                            binding.resultTime.stop()
                            viewModel.saveResultTime(
                                TrainRecord(
                                    newRecordId,
                                    numberTime,
                                    mode = "memory",
                                    args.level,
                                    ((SystemClock.elapsedRealtime() - binding.resultTime.base) / 1000.0).toString(),
                                    mistakes = binding.mistakes2Count.text.toString()
                                )
                            )
                            showEndGame()
                        }
                        viewModel.checkNumber(randomNumber, gameColumns * gameRows)
                    }
                }
            }
        }
    }

    private fun showEndGame() {
        binding.endButton.isVisible = true
        binding.resultView.isVisible = true
        binding.resultView.text = binding.resultTime.text
    }

    private fun hideNumbers() {
        for (i in 0 until binding.memoryGameTable.childCount) {
            val child = binding.memoryGameTable.getChildAt(i)
            if (child is TextView) {
                child.text = ""
            }
        }
    }

    private fun createTextView(number: Int): TextView {
        return TextView(requireContext()).apply {
            text = number.toString()
            setBackgroundResource(R.drawable.rectangle_background)
            textSize = 24f
            gravity = Gravity.CENTER
            setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

            viewModel.shouldHideNumbers.observe(viewLifecycleOwner) { shouldHide ->
                if (shouldHide) {
                    text = ""
                }
            }
        }
    }
}