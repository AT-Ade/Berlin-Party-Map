package com.example.berlinpartymap.ui.savedevents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.berlinpartymap.data.local.EventWithLineup
import com.example.berlinpartymap.data.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class SavedEventsViewModel(
    private val repository: EventRepository
) : ViewModel() {

    // Alle Events aus der DB als gemeinsame Basis für alle abgeleiteten Flows
    private val allDbEvents: StateFlow<List<EventWithLineup>> = repository.getAllSavedEvents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // -----------------------------------------------------------------------
    // Für den MapScreen: isSaved-Prüfung
    // Ein Event gilt als "favorisiert" (Herz aktiv) wenn isFavorite = true.
    // Events die nur wegen iWasThere=true in der DB sind zählen NICHT.
    // -----------------------------------------------------------------------
    val allSavedEventsIncludingHistory: StateFlow<List<EventWithLineup>> = allDbEvents
        .map { list -> list.filter { it.event.isFavorite } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // -----------------------------------------------------------------------
    // Für den SavedEventsScreen: Anstehende Favoriten (Zukunft, isFavorite=true)
    // Zeigt Events die noch nicht stattgefunden haben.
    // -----------------------------------------------------------------------
    val activeSavedEvents: StateFlow<List<EventWithLineup>> = allDbEvents
        .map { list ->
            list.filter { it.event.isFavorite && !isInPast(it.event.endTime) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // savedEvents = Alias für activeSavedEvents (wird in SavedEventDetailScreen genutzt)
    val savedEvents: StateFlow<List<EventWithLineup>> = activeSavedEvents

    // -----------------------------------------------------------------------
    // Für den SavedEventsScreen: Vergangene Favoriten (ausklappbarer Bereich)
    //
    // Zeigt Events die:
    //   - isFavorite = true (der User hat sie bewusst gespeichert)
    //   - in der Vergangenheit liegen (endTime vergangen)
    //
    // BEWUSST werden hier auch Events mit iWasThere=false angezeigt,
    // weil der User sie weiterhin als Favorit behalten hat.
    // Der "war ich da"-Status ist separat und stört hier nicht.
    // -----------------------------------------------------------------------
    val pastSavedEvents: StateFlow<List<EventWithLineup>> = allDbEvents
        .map { list ->
            list.filter { it.event.isFavorite && isInPast(it.event.endTime) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun removeFavorite(eventId: String) {
        viewModelScope.launch {
            repository.removeEventFromFavorites(eventId)
        }
    }

    // Hilfsfunktion: true wenn das Event-Ende in der Vergangenheit liegt
    private fun isInPast(endTime: String): Boolean {
        return try {
            ZonedDateTime.parse(endTime).isBefore(ZonedDateTime.now())
        } catch (e: Exception) {
            false
        }
    }
}
