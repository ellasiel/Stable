package com.eltex.shultestable.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.eltex.shultestable.MainActivity
import com.eltex.shultestable.databinding.FragmentSpeedBinding

class FragmentSpeed : Fragment() {
    private lateinit var binding: FragmentSpeedBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSpeedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        onBackPressed()
    }

    private fun setupButtons() {
        binding.easyButton.setOnClickListener {
            findNavController().navigate(FragmentSpeedDirections.goToSpeedGame("easy"))
        }
        binding.normalButton.setOnClickListener {
            findNavController().navigate(FragmentSpeedDirections.goToSpeedGame("normal"))
        }
        binding.hardButton.setOnClickListener {
            findNavController().navigate(FragmentSpeedDirections.goToSpeedGame("hard"))
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