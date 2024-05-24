package com.eltex.shultestable.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ToolbarViewModel : ViewModel() {
    private val _infoClicked = MutableStateFlow(false)
    val infoClicked = _infoClicked.asStateFlow()
    private val _exitClicked = MutableStateFlow(false)
    val exitClicked = _exitClicked.asStateFlow()
    fun onInfoClicked() {
        _infoClicked.value = true
    }

    fun onExitClicked() {
        _exitClicked.value = true
    }

    fun resetInfoClicked() {
        _infoClicked.value = false
    }

    fun resetExitClicked() {
        _exitClicked.value = false
    }
}