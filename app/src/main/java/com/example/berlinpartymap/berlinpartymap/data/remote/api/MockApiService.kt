package com.example.berlinpartymap.data.remote.api

import com.example.berlinpartymap.data.remote.dto.ArtistDto
import com.example.berlinpartymap.data.remote.dto.ClubDto
import com.example.berlinpartymap.data.remote.dto.EventDto
import com.example.berlinpartymap.berlinpartymap.data.remote.dto.berlinEvents // Import deiner Mockdaten-Liste
import java.time.LocalDate
import java.time.ZonedDateTime

class MockAPIService : APIService {

    override suspend fun getEvents(date: String): List<EventDto> {
        // 1. Ktor-Filterlogik: Nach Datum filtern
        val filteredKtorEvents = try {
            val targetDate = LocalDate.parse(date) // Parsed das übergebene "yyyy-MM-dd"

            berlinEvents.filter { event ->
                val eventDate = ZonedDateTime.parse(event.startTime).toLocalDate()
                eventDate == targetDate
            }
        } catch (e: Exception) {
            // Falls das Parsen fehlschlägt, geben wir als Fallback einfach alle Events aus
            berlinEvents
        }

        // 2. Mapping: Gefilterte Ktor-Events in Android-EventDtos umwandeln
        return filteredKtorEvents.map { ktorEvent ->
            EventDto(
                name = ktorEvent.name,
                venueName = ktorEvent.venueName,
                venueAddress = ktorEvent.venueAddress,
                latitude = ktorEvent.latitude,
                longitude = ktorEvent.longitude,
                startTime = ktorEvent.startTime,
                endTime = ktorEvent.endTime,
                description = ktorEvent.description,
                url = ktorEvent.url,
                flyerURL = ktorEvent.flyerURL,
                price = ktorEvent.price,
                lineup = ktorEvent.lineup.map { ktorArtist ->
                    ArtistDto(name = ktorArtist.name)
                }
            )
        }
    }

    override suspend fun getClubs(): List<ClubDto> = emptyList()
}