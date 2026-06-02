package com.example.berlinpartymap.data.repository

import com.example.berlinpartymap.data.local.ArtistEntity
import com.example.berlinpartymap.data.local.EventEntity
import com.example.berlinpartymap.data.local.EventWithLineup
import com.example.berlinpartymap.data.remote.dto.ArtistDto
//import com.example.berlinpartymap.data.remote.api.BpmAPI
import com.example.berlinpartymap.data.remote.dto.EventDto
import kotlinx.coroutines.flow.Flow

interface EventRepository{
    // --------------------  REST API calls -------------------

    // alle events
    //suspend fun getEvents(): List<EventDto>

    // Events nach Datum:
    suspend fun getEvents(dateString: String): List<EventDto>

    // -------------------- DAO queries --------------------

    fun getAllSavedEvents(): Flow<List<EventWithLineup>>

    suspend fun saveDtoToDatabase(dto: EventDto)

    //suspend fun saveArtistToDatabase(dto: ArtistDto)

    // Entfernt ein Event und sein Lineup aus den Favoriten
    suspend fun removeEventFromFavorites(eventId: String)

    // Prüft, ob ein Event bereits in der Datenbank vorhanden ist
    suspend fun isEventSaved(eventId: String): Boolean

    // Aktualisiert das iWasThere-Flag eines besuchten Events
    suspend fun updateEventAttendance(eventId: String, iWasThere: Boolean)

    // Speichert die Sternebewertung eines besuchten Events
    suspend fun updateEventRating(eventId: String, rating: Int)

    // Setzt das iLike-Flag eines Artists (Herz-Button im Lineup)
    suspend fun toggleArtistLike(artistId: Long, liked: Boolean)

    // Gibt alle vom Nutzer geliketen Artists zurück
    fun getLikedArtists(): Flow<List<ArtistEntity>>


}
