package com.example.berlinpartymap.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.berlinpartymap.data.remote.api.BpmAPI
import com.example.berlinpartymap.data.remote.dto.EventDto
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    private var _events = MutableStateFlow<List<EventDto>>(emptyList())
    val events = _events.asStateFlow()

    // -------- UI STATE --------
    private val _selectedEvent = MutableStateFlow<EventDto?>(null)
    val selectedEvent = _selectedEvent.asStateFlow()

    private val _eventSelected = MutableStateFlow(false)
    val eventSelected = _eventSelected.asStateFlow()

    // -------- Daten laden --------
    fun loadInitialData() {
        loadEvents()
    }

    fun loadEvents() {
        viewModelScope.launch {
            val result = BpmAPI.retrofitService.getEvents()
            _events.value = result
        }
    }

    // -------- UI Aktionen --------
    fun selectEvent(event: EventDto) {
        _selectedEvent.value = event
        _eventSelected.value = true
    }

    fun clearSelection() {
        _selectedEvent.value = null
        _eventSelected.value = false
    }
}