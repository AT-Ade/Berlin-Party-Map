package com.example.berlinpartymap.ui.map

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.berlinpartymap.data.remote.api.BpmAPI
import com.example.berlinpartymap.data.remote.dto.EventDto
import com.example.berlinpartymap.ui.helpers.createMarkerIcon
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import kotlin.collections.forEach
import org.maplibre.spatialk.geojson.*
import org.slf4j.MDC.put
import kotlin.collections.map

class MapViewModel : ViewModel() {

    private var _events = MutableStateFlow<List<EventDto>>(emptyList())
    val events = _events.asStateFlow()

    val eventFeatures: StateFlow<List<Feature<Point, JsonObject>>> = _events
        .map { eventList ->
            eventList.map { event ->
                Feature(
                    geometry = Point(Position(event.longitude, event.latitude)),
                    properties = buildJsonObject {
                        put("venueName", JsonPrimitive(event.venueName))
                        put("eventName", JsonPrimitive(event.name))
                        // Du kannst hier weitere IDs speichern, um später
                        // auf Klicks zu reagieren
                        put("id", JsonPrimitive(event.url))
                    }
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedEvent = MutableStateFlow<EventDto?>(null)
    val selectedEvent = _selectedEvent.asStateFlow()

    private val _highlightedEvent = MutableStateFlow<EventDto?>(null)
    val highlightedEvent = _highlightedEvent.asStateFlow()

    private val _eventSelected = MutableStateFlow(false)
    val eventSelected = _eventSelected.asStateFlow()

    private val _eventHighlighted = MutableStateFlow(false)
    val eventHighlighted = _eventHighlighted.asStateFlow()

    fun loadInitialData() {
        loadEvents()
    }

    fun loadEvents() {
        viewModelScope.launch {
            try {
                val result = BpmAPI.retrofitService.getEvents()
                _events.value = result
            } catch (e: Exception) {
                // Hier könnte Fehlerbehandlung stehen
            }
        }
    }

    fun createEventMarkers(){
        _events.value.forEach { event ->

        }
    }

    fun selectEvent(event: EventDto) {
        _selectedEvent.value = event
        _eventSelected.value = true
    }

    fun highlightEvent(event: EventDto) {
        _highlightedEvent.value = event
        _eventHighlighted.value = true
    }

    fun clearSelection() {
        _selectedEvent.value = null
        _eventSelected.value = false
    }

    fun clearHighlight() {
        _highlightedEvent.value = null
        _eventHighlighted.value = false
    }
}