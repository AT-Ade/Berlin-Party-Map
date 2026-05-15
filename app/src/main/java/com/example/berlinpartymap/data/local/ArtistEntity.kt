package com.example.berlinpartymap.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class ArtistEntity(
    @PrimaryKey(autoGenerate = true)
    val artistId: Long = 0,
    val eventId: String, //Fremdschlüssel
    val name: String
)