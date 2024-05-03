package com.eltex.shultestable.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.shultestable.repository.RecordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
class RecordViewModel(private val repository: RecordRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(RecordUiState())
    val uiState: StateFlow<RecordUiState> = _uiState.asStateFlow()
    init {
        repository.getRecords()
            .onEach { records ->
                _uiState.update {
                    it.copy(records = records)
                }
            }
            .launchIn(viewModelScope)
    }
    fun deleteById(id: Long) {
        repository.deleteById(id)
    }
}