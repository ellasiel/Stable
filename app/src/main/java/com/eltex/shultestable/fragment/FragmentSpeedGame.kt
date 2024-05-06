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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eltex.shultestable.R
import com.eltex.shultestable.databinding.FragmentSpeedGameBinding
import com.eltex.shultestable.model.Record
import com.eltex.shultestable.viewmodel.SpeedGameViewModel
class FragmentSpeedGame : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_speed_game, container, false)
    }
}