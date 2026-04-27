package com.example.berlinpartymap.data.repository

import com.example.berlinpartymap.data.remote.api.BpmAPI
import com.example.berlinpartymap.data.remote.dto.EventDto

class EventRepository(
    private val api: BpmAPI
) {
    suspend fun getEvents(): List<EventDto> {
        return api.retrofitService.getEvents()//.map { it.toDomain() }
    }
}