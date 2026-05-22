package com.example.berlinpartymap.data.local

import androidx.room.Embedded
import androidx.room.Relation

data class EventWithLineup(
    @Embedded val event: EventEntity,
    @Relation(
        parentColumn = "eventId",
        entityColumn = "eventId"
    )
    val lineup: List<ArtistEntity>
)
