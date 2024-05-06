package com.eltex.shultestable.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.eltex.shultestable.MainActivity
import com.eltex.shultestable.R
import com.eltex.shultestable.databinding.FragmentSpeedBinding
import com.eltex.shultestable.model.Record

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
        binding.easyBtn.setOnClickListener {
            findNavController().navigate(FragmentSpeedDirections.goToGame("easy"))
        }
        binding.hardBtn.setOnClickListener {
            findNavController().navigate(FragmentSpeedDirections.goToGame("hard"))
        }
        binding.expertBtn.setOnClickListener {
            findNavController().navigate(FragmentSpeedDirections.goToGame("expert"))
        }
        binding.exitBtn.setOnClickListener {
            exit()
        }
    }


    private fun unlockLevels(records: List<Record>) {
        for (element in records) {
            when (element.level) {
                "easy" -> {
                    binding.hardBtn.isClickable = true
                    binding.hardBtn.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary))
                }
                "hard" -> {
                    binding.expertBtn.isClickable = true
                    binding.expertBtn.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary))
                }
            }
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

    private fun exit() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Exit")
        builder.setMessage("Do you want to leave the game?")
        builder.setNegativeButton("No") { dialog, i ->

        }
        builder.setPositiveButton("Yes") { dialog, i ->
            (requireActivity() as? MainActivity)?.finish()
        }
        builder.show()
    }
}