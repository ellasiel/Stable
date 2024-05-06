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
import com.eltex.shultestable.databinding.FragmentSpeedGameBinding
import com.eltex.shultestable.db.AppDb
import com.eltex.shultestable.model.Record
import com.eltex.shultestable.repository.SQLiteRecordRepository
import com.eltex.shultestable.viewmodel.SpeedGameViewModel

class FragmentSpeedGame : Fragment() {
    private lateinit var binding: FragmentSpeedGameBinding
    private val args: FragmentSpeedGameArgs by navArgs()
    private val viewModel by viewModels<SpeedGameViewModel> {
        viewModelFactory {
            initializer {
                SpeedGameViewModel(
                    recordRepository = SQLiteRecordRepository(
                        AppDb.getInstance(requireContext().applicationContext).recordDao
                    )
                )
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpeedGameBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupGameLevel(args.level)
        setupButtons()
        setupObservers()
        viewModel.startGame()
    }

    private fun setupObservers() {
        viewModel.currentNumber.observe(viewLifecycleOwner) { currentNumber ->
            binding.currentNumber.text = currentNumber.toString()
        }
    }

    private fun setupGameLevel(level: String) {
        when (level) {
            "easy" -> setupGameTable(3, 3)
            "hard" -> setupGameTable(4, 4)
            "expert" -> setupGameTable(5, 5)
        }
    }

    private fun setupButtons() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.endBtn.setOnClickListener {
            findNavController().navigate(FragmentSpeedGameDirections.gameToHome())
        }
    }

    private fun setupGameTable(gameColumns: Int, gameRows: Int) {
        binding.gameTable.apply {
            columnCount = gameColumns
            rowCount = gameRows
        }
        val allNumbers = fillNumbers(gameColumns * gameRows)

        for (i in 0 until gameRows) {
            for (j in 0 until gameColumns) {
                val randomNumber = allNumbers.random()
                val numberTv = createTextView(randomNumber)
                binding.gameTable.addView(
                    numberTv,
                    GridLayout.LayoutParams(GridLayout.spec(i, 1f), GridLayout.spec(j, 1f))
                )
                allNumbers.remove(randomNumber)
                numberTv.setOnClickListener {
                    val currentNumber = binding.currentNumber.text.toString().toInt()
                    if (randomNumber == gameColumns * gameRows && randomNumber == currentNumber) {
                        binding.resultTime.stop()
                       viewModel.saveResultTime(
                            Record(
                                id = 2,
                                args.level,
                                (SystemClock.elapsedRealtime() - binding.resultTime.base).toString()
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
        binding.endBtn.isVisible = true
        binding.victoryTv.isVisible = true
        binding.victoryTv.text = binding.resultTime.text
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

    private fun fillNumbers(number: Int): ArrayList<Int> {
        val result = ArrayList<Int>()
        for (index in 1..number) {
            result.add(index)
        }
        return result
    }

}