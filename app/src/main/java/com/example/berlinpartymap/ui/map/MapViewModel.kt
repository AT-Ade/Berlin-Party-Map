package com.example.berlinpartymap.ui.map

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.berlinpartymap.data.remote.api.BpmAPI
import com.example.berlinpartymap.data.remote.dto.ClubDto
import com.example.berlinpartymap.data.remote.dto.EventDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    private var _events = MutableStateFlow<List<EventDto>>(emptyList())
    val events = _events.asStateFlow()

    private var _clubs = MutableStateFlow<List<ClubDto>>(emptyList())
    val clubs = _clubs.asStateFlow()

    fun loadEvents() {
        viewModelScope.launch {
            val result = BpmAPI.retrofitService.getEvents()
            _events.value = result
        }
    }

    fun loadClubs() {
        viewModelScope.launch {
            val result = BpmAPI.retrofitService.getClubs()
            _clubs.value = result
        }
    }

}