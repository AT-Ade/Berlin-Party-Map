package com.example.berlinpartymap.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.berlinpartymap.data.local.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(eventEntity: EventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtists(artists: List<ArtistEntity>)

    @Transaction
    suspend fun insertFullEvent(event: EventEntity, artists: List<ArtistEntity>) {
        insertEvent(event)
        insertArtists(artists)
    }

    @Transaction
    @Query("SELECT * FROM events ORDER BY startTime ASC")
    fun getAllSavedEvents(): Flow<List<EventWithLineup>>

    @Delete
    suspend fun delete(eventEntity: EventEntity)

    @Update
    suspend fun updateEvent(eventEntity: EventEntity)

    @Update
    suspend fun updateArtist(artistEntity: ArtistEntity)
}
