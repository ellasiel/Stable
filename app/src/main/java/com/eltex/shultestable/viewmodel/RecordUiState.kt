package com.eltex.shultestable.viewmodel

import com.eltex.shultestable.model.Record

data class RecordUiState(
    val records: List<Record> = emptyList(),
)