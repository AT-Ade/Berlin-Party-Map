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

    override suspend fun getEvents(dateString: String): List<EventDto> {
        return api.getEvents(dateString)
    }


    // ---------------- DAO Functions ----------------
    override fun getAllSavedEvents(): Flow<List<EventWithLineup>> {
        return dao.getAllSavedEvents()
    }

    override suspend fun saveDtoToDatabase(dto: EventDto) {
        // Vorhandenen DB-Eintrag prüfen, um History-Daten (iWasThere, rating) zu erhalten
        val existingEvent = dao.getEventById(dto.url)

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
            price = dto.price,
            rating = existingEvent?.rating,     // Bewertung aus History übernehmen
            iWasThere = existingEvent?.iWasThere, // History-Status übernehmen
            isFavorite = true                   // Als Favorit markieren
        )

        val artistEntities = dto.lineup.map { artistDto ->
            ArtistEntity(eventId = dto.url, name = artistDto.name)
        }

        dao.insertFullEvent(eventEntity, artistEntities)
    }

    override suspend fun removeEventFromFavorites(eventId: String) {
        val event = dao.getEventById(eventId) ?: return


        if (event.iWasThere == false) {
            dao.deleteFullEvent(eventId)
        } else {
            // iWasThere == true oder null: nur Flag entfernen, Eintrag behalten
            dao.updateFavoriteStatus(eventId, isFavorite = false)
        }
    }

    override suspend fun isEventSaved(eventId: String): Boolean {
        return dao.isEventSaved(eventId) > 0
    }

    override suspend fun updateEventAttendance(eventId: String, iWasThere: Boolean) {
        if (iWasThere) {
            // User war da → Flag setzen, fertig
            dao.updateAttendance(eventId, true)
        } else {
            // User war NICHT da (Kreuz im Bottom-Sheet).
            // Das Event NICHT löschen wenn es noch ein Favorit ist –
            // es soll weiterhin in den vergangenen Favoriten (pastSavedEvents) auftauchen.
            // Nur iWasThere = false setzen, damit es aus dem Pending-Sheet verschwindet
            // und nicht in der History-Liste auftaucht.
            val event = dao.getEventById(eventId)
            if (event != null && event.isFavorite) {
                dao.updateAttendance(eventId, false)
            } else if (event != null && !event.isFavorite) {
                // Kein Favorit und nicht besucht, wird nirgendwo mehr gebraucht
                dao.deleteFullEvent(eventId)
            }
        }
    }

    override suspend fun updateEventRating(eventId: String, rating: Int) {
        dao.updateRating(eventId, rating)
    }

    override suspend fun toggleArtistLike(artistId: Long, liked: Boolean) {
        dao.setArtistLiked(artistId, liked)
    }

    override fun getLikedArtists(): Flow<List<ArtistEntity>> {
        return dao.getLikedArtists()
    }

}
