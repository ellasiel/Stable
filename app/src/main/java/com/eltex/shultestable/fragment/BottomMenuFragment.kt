package com.eltex.shultestable.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eltex.shultestable.R
import com.eltex.shultestable.databinding.FragmentBottomMenuBinding
import com.eltex.shultestable.db.AppDb
import com.eltex.shultestable.model.GameRecord
import com.eltex.shultestable.repository.RecordRepository
import com.eltex.shultestable.repository.SQLiteRecordRepository
import com.opencsv.CSVWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class BottomMenuFragment : Fragment() {

    private lateinit var binding: FragmentBottomMenuBinding
    private lateinit var recordRepository: RecordRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomMenuBinding.inflate(inflater, container, false)

        // Инициализация репозитория
        val database = AppDb.getInstance(requireContext())
        recordRepository = SQLiteRecordRepository(database.recordDao)

        val statisticClickListener = View.OnClickListener {
            saveResultsToCSV()
        }
        val memoryClickListener = View.OnClickListener {
        }
        val speedClickListener = View.OnClickListener {
        }
        val navController =
            requireNotNull(childFragmentManager.findFragmentById(R.id.container)).findNavController()
        // Слушатель переключения вкладок
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragmentStatistic -> {
                    binding.saveResults.setOnClickListener(statisticClickListener)
                    binding.saveResults.animate()
                        .scaleX(1F)
                        .scaleY(1F)
                }

                R.id.fragmentSpeed -> {
                    binding.saveResults.setOnClickListener(speedClickListener)
                    binding.saveResults.animate()
                        .scaleX(0F)
                        .scaleY(0F)
                }

                R.id.fragmentMemory -> {
                    binding.saveResults.setOnClickListener(memoryClickListener)
                    binding.saveResults.animate()
                        .scaleX(0F)
                        .scaleY(0F)
                }
            }
        }
        binding.bottomMenu.setupWithNavController(navController)
        return binding.root
    }

    private fun saveResultsToCSV() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val recordsList = recordRepository.getRecords().firstOrNull()

                if (!recordsList.isNullOrEmpty()) {
                    val file = File(requireContext().getExternalFilesDir(null), "records.csv")

                    // Проверяем существует ли файл
                    val fileExists = file.exists()

                    FileWriter(file, !fileExists).use { fileWriter ->
                        CSVWriter(fileWriter).use { csvWriter ->
                            // Если файл не существует, записываем заголовок
                            val header =
                                arrayOf("ID", "DateTime", "Mode", "Level", "Time", "Mistakes")
                            csvWriter.writeNext(
                                header,
                                false
                            ) // Указываем false, чтобы не использовать кавычки

                            for (record in recordsList) {
                                val data = arrayOf(
                                    record.id.toString(),
                                    record.numberTime,
                                    record.mode,
                                    record.level,
                                    record.time,
                                    record.mistakes
                                )
                                csvWriter.writeNext(
                                    data,
                                    false
                                ) // Указываем false, чтобы не использовать кавычки
                            }
                        }
                    }

                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "Results saved to records.csv",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "No records to save",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}