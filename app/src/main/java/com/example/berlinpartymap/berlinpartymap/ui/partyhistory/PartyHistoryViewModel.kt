package com.example.berlinpartymap.ui.partyhistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.berlinpartymap.data.local.EventWithLineup
import com.example.berlinpartymap.data.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

// 1. Enum um die neue Option erweitern
enum class HistorySortOrder {
    DATE_ASC,        // Älteste zuerst
    DATE_DESC,       // Neueste zuerst
    RATING_ASC,      // Schlechteste Bewertung zuerst
    RATING_DESC,     // Beste Bewertung zuerst
    LIKED_ARTISTS    // Meiste gelikte Artists zuerst
}

class PartyHistoryViewModel(
    private val repository: EventRepository
) : ViewModel() {

    private val _currentSortOrder = MutableStateFlow(HistorySortOrder.DATE_DESC)
    val currentSortOrder: StateFlow<HistorySortOrder> = _currentSortOrder

    private val allDbEvents: StateFlow<List<EventWithLineup>> = repository.getAllSavedEvents()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val visitedEvents: StateFlow<List<EventWithLineup>> = allDbEvents
        .map { list ->
            list.filter { it.event.iWasThere == true && isInPast(it.event.endTime) }
        }
        .combine(_currentSortOrder) { filteredList, sortOrder ->
            when (sortOrder) {
                HistorySortOrder.DATE_ASC  -> filteredList.sortedBy { it.event.startTime }
                HistorySortOrder.DATE_DESC -> filteredList.sortedByDescending { it.event.startTime }
                HistorySortOrder.RATING_ASC -> filteredList.sortedWith(
                    compareBy(nullsLast()) { it.event.rating }
                )
                HistorySortOrder.RATING_DESC -> filteredList.sortedWith(
                    compareBy(nullsLast(reverseOrder())) { it.event.rating }
                )
                // 2. Die neue Sortierlogik einbauen
                HistorySortOrder.LIKED_ARTISTS -> filteredList.sortedWith(
                    compareByDescending<EventWithLineup> { ewl ->
                        ewl.lineup.count { it.iLike }
                    }.thenByDescending { it.event.startTime } // Bei Gleichstand: Neueste zuerst
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setSortOrder(newOrder: HistorySortOrder) {
        _currentSortOrder.value = newOrder
    }

    // -----------------------------------------------------------------------
    // Pending-Sheet: Events die der User noch nicht bestätigt/verneint hat.
    //
    // Bedingungen:
    //   - isFavorite = true      (nur favorisierte Events werden abgefragt;
    //                             nicht-favorisierte haben in der DB kein iWasThere=null
    //                             mehr, weil removeEventFromFavorites sie löscht oder
    //                             das Flag setzt)
    //   - iWasThere = null       (noch keine Antwort)
    //   - endTime in Vergangenheit (Event muss bereits vorbei sein!)
    //
    // WICHTIG: Die Bedingung isInPast() verhindert, dass zukünftige favorisierte
    // Events im Sheet auftauchen – auch wenn iWasThere noch null ist.
    // -----------------------------------------------------------------------
    val pendingConfirmationEvents: StateFlow<List<EventWithLineup>> = allDbEvents
        .map { list ->
            list.filter {
                it.event.isFavorite &&
                it.event.iWasThere == null &&
                isInPast(it.event.endTime)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Anzahl der noch nicht bestätigten vergangenen Events → Badge im Tab
    val pendingConfirmationCount: StateFlow<Int> = pendingConfirmationEvents
        .map { it.size }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Steuert ob das Bottom-Sheet zur Bestätigung sichtbar ist
    private val _showConfirmationSheet = MutableStateFlow(false)
    val showConfirmationSheet: StateFlow<Boolean> = _showConfirmationSheet

    // Öffnet das Bottom-Sheet manuell (z.B. über den Button im Screen)
    fun openConfirmationSheet() {
        _showConfirmationSheet.value = true
    }

    // Schließt das Bottom-Sheet
    fun closeConfirmationSheet() {
        _showConfirmationSheet.value = false
    }

    // Markiert ein Event als besucht (Haken-Button im Bottom-Sheet)
    fun confirmAttendance(eventId: String) {
        viewModelScope.launch {
            repository.updateEventAttendance(eventId, iWasThere = true)
        }
    }

    // Markiert ein Event als nicht besucht (Kreuz-Button im Bottom-Sheet).
    // Das Event bleibt in der DB (falls noch Favorit), fliegt nur aus dem Sheet.
    fun denyAttendance(eventId: String) {
        viewModelScope.launch {
            repository.updateEventAttendance(eventId, iWasThere = false)
        }
    }

    // Aktualisiert die Sternebewertung eines besuchten Events (aus der Detail-Ansicht)
    fun updateRating(eventId: String, rating: Int) {
        viewModelScope.launch {
            repository.updateEventRating(eventId, rating)
        }
    }

    // Setzt oder entfernt den Like eines Artists
    fun toggleArtistLike(artistId: Long, currentlyLiked: Boolean) {
        viewModelScope.launch {
            repository.toggleArtistLike(artistId, !currentlyLiked)
        }
    }

    // Gibt ein einzelnes besuchtes Event anhand seiner ID zurück (für HistoryDetailScreen)
    fun getVisitedEventById(eventId: String): EventWithLineup? {
        return visitedEvents.value.find { it.event.eventId == eventId }
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
