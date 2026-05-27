package com.example.berlinpartymap.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey
    val eventId: String,
    val name: String,
    val venueName: String,
    val venueAddress: String,
    val latitude: Double,
    val longitude: Double,
    val startTime: String,
    val endTime: String,
    val description: String,
    val flyerURL: String,
    val price: Double,
    val rating: Int? = null,
    val iWasThere: Boolean? = null,
    val isFavorite: Boolean = true // NEU: Standardmäßig true beim Speichern
)