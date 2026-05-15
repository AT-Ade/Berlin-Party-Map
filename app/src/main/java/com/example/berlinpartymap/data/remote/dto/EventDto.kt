package com.example.berlinpartymap.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    val name: String,
    val venueName: String,
    val venueAddress: String,
    val latitude: Double,
    val longitude: Double,
    val startTime: String,
    val endTime: String,
    val lineup: List<ArtistDto>,
    val description: String,
    val url: String,
    val flyerURL: String,
    val price: Double
)

