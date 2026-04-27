package com.example.berlinpartymap.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ClubDto(
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val website: String,
    val logoURL: String?
)