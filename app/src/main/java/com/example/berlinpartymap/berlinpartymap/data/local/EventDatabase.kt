package com.example.berlinpartymap.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.berlinpartymap.data.local.EventEntity

@Database(entities = [EventEntity::class, ArtistEntity::class], version = 3, exportSchema = false)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}
