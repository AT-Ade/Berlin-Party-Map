package com.example.berlinpartymap.ui.map

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.berlinpartymap.data.remote.api.BpmAPI
import com.example.berlinpartymap.data.remote.dto.EventDto
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    var events = mutableStateOf<List<EventDto>>(emptyList())
        private set

    fun loadEvents() {
        viewModelScope.launch {
            val result = BpmAPI.retrofitService.getEvents()
            events.value = result
        }
    }
}