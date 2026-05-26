package com.example.berlinpartymap.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.berlinpartymap.data.remote.dto.EventDto
import com.example.berlinpartymap.data.repository.EventRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import org.maplibre.spatialk.geojson.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Der neue UI-Status für sauberes Handling im Screen
sealed interface EventUiState {
    object Loading : EventUiState
    object Success : EventUiState
    data class Error(val message: String) : EventUiState
}

class EventViewModel(
    private val repository: EventRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow<LocalDate>(LocalDate.of(2026, 4, 26))
    val selectedDate = _selectedDate.asStateFlow()

    private val _events = MutableStateFlow<List<EventDto>>(emptyList())
    val events = _events.asStateFlow()

    // NEU: StateFlow für den aktuellen Lade-/Fehlerstatus
    private val _uiState = MutableStateFlow<EventUiState>(EventUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val apiDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    // Ersetze diesen Block in deinem EventViewModel.kt:

    val eventFeatures: StateFlow<List<Feature<Point, JsonObject>>> = _events
        .map { eventList ->
            if (eventList.isEmpty()) {
                // Wenn keine Events da sind, geben wir eine Liste mit einem "leeren/ungültigen" Feature zurück.
                // MapLibre crasht nicht, weil die Liste nicht leer ist, aber der Pin wird nirgendwo angezeigt (0.0, 0.0 ist im Meer).
                listOf(
                    Feature(
                        geometry = Point(Position(0.0, 0.0)),
                        properties = buildJsonObject {
                            put("venueName", JsonPrimitive("dummy"))
                            put("eventName", JsonPrimitive("dummy"))
                            put("id", JsonPrimitive("dummy"))
                        }
                    )
                )
            } else {
                // Wenn Events da sind, ganz normal konvertieren
                eventList.map { event ->
                    Feature(
                        geometry = Point(Position(event.longitude, event.latitude)),
                        properties = buildJsonObject {
                            put("venueName", JsonPrimitive(event.venueName))
                            put("eventName", JsonPrimitive(event.name))
                            put("id", JsonPrimitive(event.url))
                        }
                    )
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    // -------- UI STATES --------
    private val _selectedEvent = MutableStateFlow<EventDto?>(null)
    val selectedEvent = _selectedEvent.asStateFlow()

    private val _highlightedEvent = MutableStateFlow<EventDto?>(null)
    val highlightedEvent = _highlightedEvent.asStateFlow()

    private val _eventSelected = MutableStateFlow(false)
    val eventSelected = _eventSelected.asStateFlow()

    private val _cameraTarget = MutableStateFlow<Pair<Double, Double>?>(null)
    val cameraTarget = _cameraTarget.asStateFlow()

    fun changeDate(days: Long) {
        _selectedDate.value = _selectedDate.value.plusDays(days)
        clearSelection()
        clearHighlight()
        loadEventsForSelectedDate()
    }

    fun setSpecificDate(date: LocalDate) {
        _selectedDate.value = date
        clearSelection()
        clearHighlight()
        loadEventsForSelectedDate()
    }

    fun loadInitialData() {
        loadEventsForSelectedDate()
    }

    // Verbessertes Laden mit Zuweisung des UI-States
    fun loadEventsForSelectedDate() {
        viewModelScope.launch {
            _uiState.value = EventUiState.Loading // 1. Lade-Zustand setzen
            try {
                val dateString = _selectedDate.value.format(apiDateFormatter)
                val result = repository.getEvents(dateString).sortedBy { it.price }

                _events.value = result
                _uiState.value = EventUiState.Success // 2. Erfolg setzen (auch wenn Liste leer ist!)
            } catch (e: Exception) {
                _events.value = emptyList()
                // 3. Fehlerzustand setzen bei Netzwerk- oder Serverproblemen
                _uiState.value = EventUiState.Error(e.localizedMessage ?: "Netzwerkfehler aufgetreten")
            }
        }
    }

    // -------- Lokale Datenspeicherung --------
    fun saveEventToFavorites(eventDto: EventDto) {
        viewModelScope.launch {
            try { repository.saveDtoToDatabase(eventDto) } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun removeEventFromFavorites(eventId: String) {
        viewModelScope.launch {
            try { repository.removeEventFromFavorites(eventId) } catch (e: Exception) { e.printStackTrace() }
        }
    }

    // -------- UI Aktionen --------
    fun selectEvent(event: EventDto) {
        _selectedEvent.value = event
        _eventSelected.value = true
        _cameraTarget.value = Pair(event.longitude, event.latitude)
    }

    fun highlightEvent(event: EventDto) {
        _highlightedEvent.value = event
        _cameraTarget.value = Pair(event.longitude, event.latitude)
    }

    fun onCameraTargetConsumed() { _cameraTarget.value = null }
    fun clearSelection() { _selectedEvent.value = null; _eventSelected.value = false }
    fun clearHighlight() { _highlightedEvent.value = null }

    fun findEventById(id: String): EventDto? {
        return _events.value.find { it.url == id }
    }
}