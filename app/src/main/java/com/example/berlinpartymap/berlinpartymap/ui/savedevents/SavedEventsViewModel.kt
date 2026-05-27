package com.example.berlinpartymap.ui.savedevents

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

enum class FavoritesSortOrder {
    DATE_ASC,
    DATE_DESC,
    PRICE_ASC,
    PRICE_DESC,
    LIKED_ARTISTS  // Meiste gelikte Artists zuerst, dann nach Preis
}

class SavedEventsViewModel(
    private val repository: EventRepository
) : ViewModel() {

    private val _currentSortOrder = MutableStateFlow(FavoritesSortOrder.DATE_ASC)
    val currentSortOrder: StateFlow<FavoritesSortOrder> = _currentSortOrder

    private val allDbEvents: StateFlow<List<EventWithLineup>> = repository.getAllSavedEvents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allSavedEventsIncludingHistory: StateFlow<List<EventWithLineup>> = allDbEvents
        .map { list -> list.filter { it.event.isFavorite } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Liked artist names werden aus den gespeicherten Events selbst abgeleitet
    val likedArtistNames: StateFlow<Set<String>> = allDbEvents
        .map { list ->
            list.flatMap { it.lineup }
                .filter { it.iLike }
                .map { it.name }
                .toSet()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val activeSavedEvents: StateFlow<List<EventWithLineup>> = combine(
        allDbEvents, _currentSortOrder, likedArtistNames
    ) { list, sortOrder, likedNames ->
        list.filter { it.event.isFavorite && !isInPast(it.event.endTime) }
            .applySortOrder(sortOrder, likedNames)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedEvents: StateFlow<List<EventWithLineup>> = activeSavedEvents

    val pastSavedEvents: StateFlow<List<EventWithLineup>> = combine(
        allDbEvents, _currentSortOrder, likedArtistNames
    ) { list, sortOrder, likedNames ->
        list.filter { it.event.isFavorite && isInPast(it.event.endTime) }
            .applySortOrder(sortOrder, likedNames)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setSortOrder(newOrder: FavoritesSortOrder) {
        _currentSortOrder.value = newOrder
    }

    fun removeFavorite(eventId: String) {
        viewModelScope.launch {
            repository.removeEventFromFavorites(eventId)
        }
    }

    private fun List<EventWithLineup>.applySortOrder(
        sortOrder: FavoritesSortOrder,
        likedNames: Set<String>
    ): List<EventWithLineup> {
        return when (sortOrder) {
            FavoritesSortOrder.DATE_ASC      -> sortedBy { it.event.startTime }
            FavoritesSortOrder.DATE_DESC     -> sortedByDescending { it.event.startTime }
            FavoritesSortOrder.PRICE_ASC     -> sortedBy { it.event.price }
            FavoritesSortOrder.PRICE_DESC    -> sortedByDescending { it.event.price }
            FavoritesSortOrder.LIKED_ARTISTS -> sortedWith(
                compareByDescending<EventWithLineup> { ewl ->
                    ewl.lineup.count { likedNames.contains(it.name) }
                }.thenBy { it.event.price }
            )
        }
    }

    private fun isInPast(endTime: String): Boolean {
        return try {
            ZonedDateTime.parse(endTime).isBefore(ZonedDateTime.now())
        } catch (e: Exception) {
            false
        }
    }
}
