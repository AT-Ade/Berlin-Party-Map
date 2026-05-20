package com.example.berlinpartymap.ui.savedevents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.berlinpartymap.data.local.EventWithLineup
import com.example.berlinpartymap.data.remote.dto.EventDto
import com.example.berlinpartymap.data.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SavedEventsViewModelUiState(
    val something: String = ""
)

class SavedEventsViewModel(
    private val repository: EventRepository // Injection via Koin
) : ViewModel() {

    // Wir greifen direkt auf den Flow des Repositories zu
    val savedEvents: StateFlow<List<EventWithLineup>> = repository.getAllSavedEvents()
        .stateIn(
            scope = viewModelScope,
            // Hält den Datenstrom aktiv, solange der Screen sichtbar ist (plus 5 Sek. Puffer)
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}


