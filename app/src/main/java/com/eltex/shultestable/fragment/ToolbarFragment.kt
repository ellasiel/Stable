package com.eltex.shultestable.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eltex.shultestable.MainActivity
import com.eltex.shultestable.R
import com.eltex.shultestable.databinding.FragmentToolbarBinding
import com.eltex.shultestable.viewmodel.ToolbarViewModel

class ToolbarFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentFragmentManager.beginTransaction()
            .setPrimaryNavigationFragment(this)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentToolbarBinding.inflate(inflater, container, false)
        val navController =
            requireNotNull(childFragmentManager.findFragmentById(R.id.container)).findNavController()
        binding.toolbar.setupWithNavController(navController)
        val toolbarViewModel by activityViewModels<ToolbarViewModel>()

        val infoButton: ImageButton = binding.toolbar.findViewById(R.id.info_button)
        infoButton.setOnClickListener {
            toolbarViewModel.onInfoClicked()
        }
        val exitButton: ImageButton = binding.toolbar.findViewById(R.id.exit_button)
        exitButton.setOnClickListener {
            toolbarViewModel.onExitClicked()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            toolbarViewModel.infoClicked.collect { clicked ->
                if (clicked) {
                    showInfoDialog()
                    toolbarViewModel.resetInfoClicked()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            toolbarViewModel.exitClicked.collect { clicked ->
                if (clicked) {
                    exit()
                    toolbarViewModel.resetExitClicked()
                }
            }
        }

        return binding.root
    }

    private fun showInfoDialog() {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.infoTitle)
                .setMessage(R.string.infoText)
                .setPositiveButton(android.R.string.ok, null)
                .show()
        }
    }
    private fun exit() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.exit)
        builder.setMessage(R.string.exitann)
        builder.setNegativeButton(R.string.no) { dialog, i ->

        }
        builder.setPositiveButton(R.string.yes) { dialog, i ->
            (requireActivity() as? MainActivity)?.finish()
        }
        builder.show()
    }
}