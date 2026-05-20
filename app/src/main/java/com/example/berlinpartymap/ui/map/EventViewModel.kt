package com.example.berlinpartymap.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.berlinpartymap.data.remote.api.BpmAPI
import com.example.berlinpartymap.data.remote.dto.EventDto
import com.example.berlinpartymap.data.repository.EventRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import org.maplibre.spatialk.geojson.*

class EventViewModel(
    private val repository: EventRepository // Injection
) : ViewModel() {

    private var _events = MutableStateFlow<List<EventDto>>(emptyList())
    val events = _events.asStateFlow()

    // Konvertiert Event-Liste in GeoJSON Features für MapLibre
    val eventFeatures: StateFlow<List<Feature<Point, JsonObject>>> = _events
        .map { eventList ->
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
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    // -------- UI STATE --------
    private val _selectedEvent = MutableStateFlow<EventDto?>(null)
    val selectedEvent = _selectedEvent.asStateFlow()

    private val _highlightedEvent = MutableStateFlow<EventDto?>(null)
    val highlightedEvent = _highlightedEvent.asStateFlow()

    private val _eventSelected = MutableStateFlow(false)
    val eventSelected = _eventSelected.asStateFlow()

    private val _eventHighlighted = MutableStateFlow(false)
    val eventHighlighted = _eventHighlighted.asStateFlow()

    // Kamera-Ziel: lng/lat — null bedeutet kein Sprung ausstehend
    private val _cameraTarget = MutableStateFlow<Pair<Double, Double>?>(null)
    val cameraTarget = _cameraTarget.asStateFlow()

    // -------- Daten laden --------
    fun loadInitialData() {
        loadEvents()
    }

    fun loadEvents() {
        viewModelScope.launch {
            try {
                val result = repository.getEvents()
                _events.value = result
            } catch (e: Exception) {

            }
        }
    }

    // -------- Lokale Datenspeicherung --------

    //Speichert ein Event (inklusive Lineup/Artists) in der lokalen Raum-Datenbank.
    fun saveEventToFavorites(eventDto: EventDto) {
        viewModelScope.launch {
            try {
                repository.saveDtoToDatabase(eventDto)
                // Hier könntest du optional ein "Erfolgreich gespeichert"-Feedback für die UI triggern
            } catch (e: Exception) {
                // Wichtig, falls beim DB-Insert etwas schiefgeht (z.B. Constraint-Fehler)
                e.printStackTrace()
            }
        }
    }

    fun getSavedEvents(){

    }



    // -------- UI Aktionen --------
    fun selectEvent(event: EventDto) {
        _selectedEvent.value = event
        _eventSelected.value = true
        _cameraTarget.value = Pair(event.longitude, event.latitude)
    }

    fun highlightEvent(event: EventDto) {
        _highlightedEvent.value = event
        _eventHighlighted.value = true
        _cameraTarget.value = Pair(event.longitude, event.latitude)
    }

    /** Muss nach dem Kamerasprung aufgerufen werden, damit nicht erneut gesprungen wird */
    fun onCameraTargetConsumed() {
        _cameraTarget.value = null
    }

    fun clearSelection() {
        _selectedEvent.value = null
        _eventSelected.value = false
    }

    fun clearHighlight() {
        _highlightedEvent.value = null
        _eventHighlighted.value = false
    }

    /** Sucht ein Event anhand seiner URL (= eindeutige ID) */
    fun findEventById(id: String): EventDto? {
        return _events.value.find { it.url == id }
    }
}
