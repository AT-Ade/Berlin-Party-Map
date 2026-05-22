package com.example.berlinpartymap.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.berlinpartymap.data.local.EventEntity

@Database(entities = [EventEntity::class, ArtistEntity::class], version = 2, exportSchema = false)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

//    companion object {
//        @Volatile
//        private var Instance: EventDatabase? = null
//
//        fun getDatabase(context: Context): EventDatabase {
//            return Instance ?: synchronized(this) {
//                Room.databaseBuilder(context, EventDatabase::class.java, "event_database")
//                    .build().also { Instance = it }
//            }
//        }
//    }
}
