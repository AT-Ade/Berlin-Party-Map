package com.example.berlinpartymap.data.repository

import com.example.berlinpartymap.data.local.ArtistEntity
import com.example.berlinpartymap.data.local.EventDao
import com.example.berlinpartymap.data.local.EventEntity
import com.example.berlinpartymap.data.local.EventWithLineup
import com.example.berlinpartymap.data.remote.api.APIService
import com.example.berlinpartymap.data.remote.dto.EventDto
import kotlinx.coroutines.flow.Flow

class EventRepositoryImpl(
    private val dao: EventDao,
    private val api: APIService
) : EventRepository {

    // ---------------- API Functions ----------------
//    override suspend fun getEvents(): List<EventDto> {
//            return api.getEvents()//.map { it.toDomain() }
//    }

    // Passe die Implementierung an:
    override suspend fun getEvents(dateString: String): List<EventDto> {
        return api.getEvents(dateString) // Reicht das Datum an die API weiter
    }


    // ---------------- DAO Functions ----------------
    override fun getAllSavedEvents(): Flow<List<EventWithLineup>> {
        return dao.getAllSavedEvents()
    }

    override suspend fun saveDtoToDatabase(dto: EventDto) {
        // 1. Erstelle die EventEntity aus dem DTO
        val eventEntity = EventEntity(
            eventId = dto.url,
            name = dto.name,
            venueName = dto.venueName,
            venueAddress = dto.venueAddress,
            latitude = dto.latitude,
            longitude = dto.longitude,
            startTime = dto.startTime,
            endTime = dto.endTime,
            description = dto.description,
            flyerURL = dto.flyerURL,
            price = dto.price
        )

        // 2. Erstelle die Liste der ArtistEntities aus der Liste im DTO
        val artistEntities = dto.lineup.map { artistDto ->
            ArtistEntity(
                eventId = dto.url, // Wichtig: Die ID verknüpft Artist mit Event
                name = artistDto.name
            )
        }

        // 3. Beides zusammen über die DAO-Transaktion speichern
        dao.insertFullEvent(eventEntity, artistEntities)
    }

    // Entfernt das Event und alle zugehörigen Artists direkt per ID —
    // kein getAllSavedEvents().first() mehr nötig, das einen teuren Flow-Observer öffnet.
    override suspend fun removeEventFromFavorites(eventId: String) {
        dao.deleteFullEvent(eventId)
    }

    // Gibt true zurück, wenn das Event bereits in der Datenbank vorhanden ist
    override suspend fun isEventSaved(eventId: String): Boolean {
        return dao.isEventSaved(eventId) > 0
    }

    // Setzt das iWasThere-Flag direkt per SQL-Update —
    // kein Read-Modify-Write über getAllSavedEvents().first() mehr nötig.
    override suspend fun updateEventAttendance(eventId: String, iWasThere: Boolean) {
        dao.updateAttendance(eventId, iWasThere)
    }

    // Aktualisiert die Sternebewertung direkt per SQL-Update —
    // kein Read-Modify-Write über getAllSavedEvents().first() mehr nötig.
    override suspend fun updateEventRating(eventId: String, rating: Int) {
        dao.updateRating(eventId, rating)
    }

    // Setzt oder entfernt den Like eines Artists (Herz-Icon im Lineup)
    override suspend fun toggleArtistLike(artistId: Long, liked: Boolean) {
        dao.setArtistLiked(artistId, liked)
    }

    // Gibt alle Artists zurück, die der Nutzer geliked hat
    override fun getLikedArtists(): Flow<List<ArtistEntity>> {
        return dao.getLikedArtists()
    }

    //TODO update
}
