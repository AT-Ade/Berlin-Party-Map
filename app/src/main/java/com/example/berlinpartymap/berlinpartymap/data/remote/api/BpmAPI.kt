package com.example.berlinpartymap.data.remote.api

import com.example.berlinpartymap.data.remote.dto.ClubDto
import com.example.berlinpartymap.data.remote.dto.EventDto
import retrofit2.http.GET
import retrofit2.http.Query // WICHTIG FÜR DEN PARAMETER

interface APIService {
    // Hier übergeben wir das Datum als String an die API
    @GET("events")
    suspend fun getEvents(
        @Query("date") date: String
    ): List<EventDto>

    @GET("clubs")
    suspend fun getClubs(): List<ClubDto>
}