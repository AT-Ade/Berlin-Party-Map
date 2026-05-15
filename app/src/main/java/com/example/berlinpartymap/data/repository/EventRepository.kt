package com.example.berlinpartymap.data.repository

import com.example.berlinpartymap.data.local.EventEntity
import com.example.berlinpartymap.data.local.EventWithLineup
//import com.example.berlinpartymap.data.remote.api.BpmAPI
import com.example.berlinpartymap.data.remote.dto.EventDto
import kotlinx.coroutines.flow.Flow

interface EventRepository{
    // --------------------  REST API calls -------------------

    suspend fun getEvents(): List<EventDto>


    // -------------------- DAO queries --------------------

    fun getAllSavedEvents(): Flow<List<EventWithLineup>>

    suspend fun saveDtoToDatabase(dto: EventDto)


    // TODO update

}