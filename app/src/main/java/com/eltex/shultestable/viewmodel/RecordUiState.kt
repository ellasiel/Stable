package com.eltex.shultestable.viewmodel

import com.eltex.shultestable.model.GameRecord

data class RecordUiState(
    val records: List<GameRecord> = emptyList(),
)