package com.example.berlinpartymap.ui.partyhistory

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

class PartyHistoryViewModel(
    private val repository: EventRepository // Injection via Koin
) : ViewModel() {

    // Einzige DB-Subscription — alle abgeleiteten Flows teilen sich diesen einen StateFlow.
    // Früher hatten visitedEvents und pendingConfirmationEvents jeweils eigene map()-Chains
    // auf getAllSavedEvents(), was zwei separate DB-Observer öffnete.
    private val allSavedEvents: StateFlow<List<EventWithLineup>> = repository.getAllSavedEvents()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Alle Events, die bereits in der Vergangenheit liegen und besucht wurden (iWasThere = true)
    val visitedEvents: StateFlow<List<EventWithLineup>> = allSavedEvents
        .map { list -> list.filter { it.event.iWasThere == true && isInPast(it.event.endTime) } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Events in der Vergangenheit, bei denen der Nutzer noch nicht angegeben hat ob er da war.
    // Diese werden im Bottom-Sheet zur Bestätigung angezeigt.
    val pendingConfirmationEvents: StateFlow<List<EventWithLineup>> = allSavedEvents
        .map { list -> list.filter { it.event.iWasThere == null && isInPast(it.event.endTime) } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Anzahl der noch nicht bestätigten vergangenen Events → wird für den Badge verwendet.
    // Abgeleitet von pendingConfirmationEvents statt nochmal allSavedEvents zu subscriben.
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

    // Gibt ein einzelnes besuchtes Event anhand seiner ID zurück (für Detail-Screen)
    fun getVisitedEventById(eventId: String): EventWithLineup? {
        return visitedEvents.value.find { it.event.eventId == eventId }
    }

    // Hilfsfunktion: Gibt true zurück, wenn das Event-Ende in der Vergangenheit liegt
    private fun isInPast(endTime: String): Boolean {
        return try {
            ZonedDateTime.parse(endTime).isBefore(ZonedDateTime.now())
        } catch (e: Exception) {
            false
        }
    }
}
