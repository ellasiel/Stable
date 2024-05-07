package com.eltex.shultestable.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
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
        return binding.root
    }
}