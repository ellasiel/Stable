package com.eltex.shultestable.viewmodel

import com.eltex.shultestable.model.TrainRecord

data class RecordUiState(
    val records: List<TrainRecord> = emptyList(),
)