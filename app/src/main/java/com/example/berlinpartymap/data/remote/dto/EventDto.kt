package com.example.berlinpartymap.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    val name: String,
    val venueName: String,
    val latitude: Double,
    val longitude: Double,
    val startTime: String, // ISO 8601 Format empfohlen
    val endTime: String,
    val lineup: List<String>,
    val description: String,
    val url: String,
    val flyerURL: String
)