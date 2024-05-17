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
import com.eltex.shultestable.databinding.FragmentMemoryGameBinding
import com.eltex.shultestable.db.AppDb
import com.eltex.shultestable.model.Record
import com.eltex.shultestable.repository.SQLiteRecordRepository
import com.eltex.shultestable.utils.DateTimeUtils
import com.eltex.shultestable.viewmodel.MemoryGameViewModel

class FragmentMemoryGame : Fragment() {
    private lateinit var binding: FragmentMemoryGameBinding
    private val args: FragmentMemoryGameArgs by navArgs()
    private val viewModel by viewModels<MemoryGameViewModel> {
        viewModelFactory {
            initializer {
                MemoryGameViewModel(
                    recordRepository = SQLiteRecordRepository(
                        AppDb.getInstance(requireContext().applicationContext).recordDao
                    )
                )
            }
        }
    }

    private var newRecordId: Long = 0L
    private lateinit var numberTime: String
    private var isClickable = false // Переменная для управления кликабельностью

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemoryGameBinding.inflate(layoutInflater)
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
        viewModel.currentNumber.observe(viewLifecycleOwner) { actualNumber ->
            binding.currentNumber.text = actualNumber.toString()
        }

        viewModel.mistakes2Count.observe(viewLifecycleOwner) { count ->
            // Обновляем отображение количества ошибок
            binding.mistakes2Count.text = count.toString()
        }

        viewModel.shouldHideNumbers.observe(viewLifecycleOwner) { shouldHide ->
            if (shouldHide) {
                hideNumbers()
                // Включаем кликабельность квадратиков и стартуем таймер через 5 секунд после исчезновения цифр
                enableClickAndStartTimerAfterDelay(0)
            }
        }
    }

    private fun enableClickAndStartTimerAfterDelay(delayMillis: Long) {
        view?.postDelayed({
            isClickable = true
            binding.resultTime.base = SystemClock.elapsedRealtime()
            binding.resultTime.start() // Запуск таймера
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
            findNavController().navigate(FragmentMemoryGameDirections.memoryGameToMemory())
        }
    }

    private fun setupGameTable(gameColumns: Int, gameRows: Int) {
        binding.memoryGameTable.apply {
            columnCount = gameColumns
            rowCount = gameRows
        }
        val allNumbers = fillNumbers(gameColumns * gameRows)

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
                    if (isClickable) { // Проверяем, кликабелен ли квадратик
                        val actualNumber = binding.currentNumber.text.toString().toInt()
                        if (randomNumber == gameColumns * gameRows && randomNumber == actualNumber) {
                            binding.resultTime.stop()
                            viewModel.saveResultTime(
                                Record(
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

    private fun fillNumbers(number: Int): ArrayList<Int> {
        val result = ArrayList<Int>()
        for (index in 1..number) {
            result.add(index)
        }
        return result
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