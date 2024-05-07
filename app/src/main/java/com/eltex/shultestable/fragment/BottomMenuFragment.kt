package com.eltex.shultestable.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eltex.shultestable.R
import com.eltex.shultestable.databinding.FragmentBottomMenuBinding
class BottomMenuFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentBottomMenuBinding.inflate(inflater, container, false)
        val statisticClickListener = View.OnClickListener {
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
                        .scaleX(1F)
                        .scaleY(1F)
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
}