package com.example.berlinpartymap.ui.savedevents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.berlinpartymap.data.local.EventWithLineup
import com.example.berlinpartymap.data.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// 1. Definition der Sortier-Optionen für die gespeicherten Events
enum class SavedEventsSortOrder {
    DATE_ASC,            // Chronologisch (nächstes Event zuerst)
    PRICE_ASC,           // Günstigste zuerst (Gratis/Keine Angabe unten)
    PRICE_DESC,          // Teuerste zuerst (Gratis/Keine Angabe unten)
    LIKED_ARTISTS_DESC   // Meiste gelikte Artists im Lineup zuerst
}

class SavedEventsViewModel(
    private val repository: EventRepository
) : ViewModel() {

    // 2. State für die aktuelle Sortierung (Standard: Nach Datum)
    private val _currentSortOrder = MutableStateFlow(SavedEventsSortOrder.DATE_ASC)
    val currentSortOrder: StateFlow<SavedEventsSortOrder> = _currentSortOrder

    // 3. Kombinieren des Datenbank-Flows mit unserem Sortier-Zustand
    val savedEvents: StateFlow<List<EventWithLineup>> = repository.getAllSavedEvents()
        .combine(_currentSortOrder) { eventList, sortOrder ->
            when (sortOrder) {
                // Nach Datum aufsteigend sortieren
                SavedEventsSortOrder.DATE_ASC -> eventList.sortedBy { it.event.startTime }

                // Preis aufsteigend (Günstigste zuerst, unbewertet/null am Ende)
                SavedEventsSortOrder.PRICE_ASC -> eventList.sortedWith(
                    compareBy(nullsLast()) { it.event.price }
                )

                // Preis absteigend (Teuerste zuerst, unbewertet/null am Ende)
                SavedEventsSortOrder.PRICE_DESC -> eventList.sortedWith(
                    compareBy(nullsLast(reverseOrder())) { it.event.price }
                )

                // Meiste gelikte Artists zuerst
                // Wir zählen, wie viele Artists im `lineup` das Flag `isLiked == true` haben
                SavedEventsSortOrder.LIKED_ARTISTS_DESC -> eventList.sortedByDescending { item ->
                    item.lineup.count { artist -> artist.iLike }
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // 4. Setter für die UI
    fun setSortOrder(newOrder: SavedEventsSortOrder) {
        _currentSortOrder.value = newOrder
    }

    fun removeEventFromFavorites(eventId: String) {
        viewModelScope.launch {
            // Entweder löschst du es komplett oder setzt iWasThere / saved zurück,
            // je nachdem wie deine Repository-Methode benannt ist:
            repository.updateEventAttendance(eventId, iWasThere = false)
            // ODER falls du ein direktes Löschen hast: repository.deleteSavedEvent(eventId)
        }
    }
}