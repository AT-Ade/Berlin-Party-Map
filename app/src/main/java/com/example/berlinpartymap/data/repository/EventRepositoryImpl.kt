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
    override suspend fun getEvents(): List<EventDto> {
            return api.getEvents()//.map { it.toDomain() }
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

    //TODO update
}
