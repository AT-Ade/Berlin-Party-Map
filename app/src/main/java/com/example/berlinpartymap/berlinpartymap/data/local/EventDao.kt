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

    // Einmalige (suspend) Abfrage eines einzelnen Events — ohne Flow-Overhead.
    // Wird im Repository für Update/Delete genutzt, damit kein getAllSavedEvents().first()
    // geöffnet werden muss, was unnötig teuer ist und ANRs verursachen kann.
    @Query("SELECT * FROM events WHERE eventId = :eventId LIMIT 1")
    suspend fun getEventById(eventId: String): EventEntity?

    @Delete
    suspend fun delete(eventEntity: EventEntity)

    // Löscht alle Artists eines bestimmten Events (vor dem Entfavorisieren)
    @Query("DELETE FROM artists WHERE eventId = :eventId")
    suspend fun deleteArtistsByEventId(eventId: String)

    // Löscht ein Event direkt anhand seiner ID — kein vorheriges Lesen nötig
    @Query("DELETE FROM events WHERE eventId = :eventId")
    suspend fun deleteEventById(eventId: String)

    // Atomare Transaktion: Event + zugehörige Artists komplett entfernen
    @Transaction
    suspend fun deleteFullEvent(eventId: String) {
        deleteArtistsByEventId(eventId)
        deleteEventById(eventId)
    }

    @Update
    suspend fun updateEvent(eventEntity: EventEntity)

    @Update
    suspend fun updateArtist(artistEntity: ArtistEntity)

    // Setzt das iLike-Flag eines Artists anhand seiner ID
    @Query("UPDATE artists SET iLike = :liked WHERE artistId = :artistId")
    suspend fun setArtistLiked(artistId: Long, liked: Boolean)

    // Gibt alle Artists zurück, die der Nutzer geliked hat
    @Query("SELECT * FROM artists WHERE iLike = 1")
    fun getLikedArtists(): Flow<List<ArtistEntity>>

    @Query("UPDATE events SET isFavorite = :isFavorite WHERE eventId = :eventId")
    suspend fun updateFavoriteStatus(eventId: String, isFavorite: Boolean)

    // GEÄNDERT: Ein Event gilt jetzt auf der Detailseite als "gespeichert/Herz aktiv",
    // wenn isFavorite explizit true ist
    @Query("SELECT COUNT(*) FROM events WHERE eventId = :eventId AND isFavorite = 1")
    suspend fun isEventSaved(eventId: String): Int
    // Setzt das iWasThere-Flag direkt per SQL — kein Lesen + Kopieren der Entity nötig
    @Query("UPDATE events SET iWasThere = :iWasThere WHERE eventId = :eventId")
    suspend fun updateAttendance(eventId: String, iWasThere: Boolean)

    // Setzt die Bewertung direkt per SQL — kein Lesen + Kopieren der Entity nötig
    @Query("UPDATE events SET rating = :rating WHERE eventId = :eventId")
    suspend fun updateRating(eventId: String, rating: Int)
}
