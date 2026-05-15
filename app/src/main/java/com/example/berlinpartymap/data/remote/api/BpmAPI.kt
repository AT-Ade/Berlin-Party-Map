package com.example.berlinpartymap.data.remote.api

import com.example.berlinpartymap.data.remote.dto.ClubDto
import com.example.berlinpartymap.data.remote.dto.EventDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET



//private val moshi = Moshi.Builder()
//    .add(KotlinJsonAdapterFactory())
//    .build()
//
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(MoshiConverterFactory.create(moshi))
//    .baseUrl(BASE_URL)
//    .build()

interface APIService {
    @GET("events")
    suspend fun getEvents(): List<EventDto>

    @GET("clubs")
    suspend fun getClubs(): List<ClubDto>
}

//object BpmAPI {
//    val retrofitService: APIService by lazy { retrofit.create(APIService::class.java) }
//}


