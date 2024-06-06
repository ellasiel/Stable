package com.eltex.shultestable.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.eltex.shultestable.databinding.FragmentMemoryBinding

class FragmentMemory : Fragment() {
    private lateinit var binding: FragmentMemoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        onBackPressed()
    }

    private fun setupButtons() {
        binding.memoryEasyButton.setOnClickListener {
            findNavController().navigate(FragmentMemoryDirections.goToMemoryTrain("easy"))
        }
        binding.memoryNormalButton.setOnClickListener {
            findNavController().navigate(FragmentMemoryDirections.goToMemoryTrain("normal"))
        }
        binding.memoryHardButton.setOnClickListener {
            findNavController().navigate(FragmentMemoryDirections.goToMemoryTrain("hard"))
        }
    }

    private fun onBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                return
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

}