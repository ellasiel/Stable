package com.eltex.shultestable.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrainRecord(
    @SerialName("id")
    var id: Long = 0L,
    @SerialName("numberTime")
    val numberTime: String = "",
    @SerialName("mode")
    val mode: String = "",
    @SerialName("level")
    val level: String = "",
    @SerialName("time")
    val time: String = "",
    @SerialName("mistakes")
    val mistakes: String = "",
)