package com.eltex.shultestable.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Record(
    @SerialName("id")
    var id: Long = 0L,
    @SerialName("level")
    val level: String = "",
    @SerialName("time")
    val time: String = "",
)