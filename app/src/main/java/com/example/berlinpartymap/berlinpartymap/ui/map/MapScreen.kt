package com.example.berlinpartymap.ui.map

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.berlinpartymap.data.remote.dto.EventDto
import com.example.berlinpartymap.ui.components.Background
import com.example.berlinpartymap.ui.savedevents.SavedEventsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.spatialk.geojson.Position
import kotlin.String

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    eventViewModel: EventViewModel = koinViewModel(),
    savedEventsViewModel: SavedEventsViewModel = koinViewModel()
) {
    val events by eventViewModel.events.collectAsState()
    val eventFeatures by eventViewModel.eventFeatures.collectAsState()
    val selectedEvent by eventViewModel.selectedEvent.collectAsState()
    val highlightedEvent by eventViewModel.highlightedEvent.collectAsState()
    val eventSelected by eventViewModel.eventSelected.collectAsState()
    val cameraTarget by eventViewModel.cameraTarget.collectAsState()

    val savedEventsWithLineup by savedEventsViewModel.savedEvents.collectAsState()

    var mapListToggle by remember { mutableStateOf(true) }

    // -------- Animationen --------
    // HINWEIS: Diese Animationen lösen bei jedem Frame eine Recomposition des gesamten
    // MapScreen aus. Das ist kein Problem mehr, weil MapContainer die FeatureCollection
    // über `remember(locations)` cacht – MapLibre lädt die Karte also nicht neu,
    // nur weil sich mapHeight/listHeight geändert hat.
    val mapHeight by animateDpAsState(
        targetValue = if (mapListToggle) 600.dp else 200.dp,
        animationSpec = tween(200)
    )
    val listHeight by animateDpAsState(
        targetValue = if (mapListToggle) 200.dp else 600.dp,
        animationSpec = tween(200)
    )
    val mapElevation by animateDpAsState(
        targetValue = if (mapListToggle) 20.dp else 5.dp
    )
    val listElevation by animateDpAsState(
        targetValue = if (!mapListToggle) 20.dp else 5.dp
    )

    // Back Navigation für Detail View
    if (selectedEvent != null) {
        BackHandler { eventViewModel.clearSelection() }
    }

    // -------- MapLibre Kamera --------

    // cameraState wird hier erstellt und an MapContainer weitergegeben

    val cameraState = rememberCameraState(
        CameraPosition(
            target = Position(13.4050, 52.5200),
            zoom = 11.0,

        )
    )

    val coroutineScope = rememberCoroutineScope()

    // Reagiert auf Kamera-Ziel aus dem ViewModel (z.B. wenn aus der Liste ein Event geöffnet wird)
//    LaunchedEffect(cameraTarget) {
//        val target = cameraTarget ?: return@LaunchedEffect
//        coroutineScope.launch {
//            cameraState.animateTo(
//                CameraPosition(
//                    target = Position(target.first, target.second),
//                    zoom = 15.0
//                )
//            )
//        }
//        eventViewModel.onCameraTargetConsumed()
//    }
    LaunchedEffect(cameraTarget) {
        cameraTarget?.let { target ->
            cameraState.animateTo(
                CameraPosition(
                    target = Position(target.first, target.second),
                    zoom = 15.0
                )
            )
            eventViewModel.onCameraTargetConsumed()
        }
    }

    // -------- Daten laden --------
    LaunchedEffect(Unit) {
        eventViewModel.loadInitialData()
    }

    // Prüft ob das aktuell ausgewählte Event bereits als Favorit gespeichert ist
    val isCurrentEventSaved = savedEventsWithLineup.any {
        it.event.eventId.trim().lowercase() == selectedEvent?.url?.trim()?.lowercase()
    }

    // Alle Artist-Namen extrahieren, die in der DB als geliked (iLike = true) markiert sind
    val likedArtistNames: Set<String> by remember(savedEventsWithLineup) {
        derivedStateOf {
            savedEventsWithLineup
                .flatMap { eventWithLineup -> eventWithLineup.lineup } // Holt die Liste der ArtistEntity
                .filter { artistEntity -> artistEntity.iLike }          // Filtert nach iLike == true
                .map { artistEntity -> artistEntity.name }              // Holt den Namen (String)
                .toSet()                                                // Macht ein Set<String> daraus
        }
    }

    // -------- UI --------
    Box {
        Background()

        Column {
            MapContainer(
                cameraState = cameraState,
                highlightedEvent = highlightedEvent,
                onCloseInfo = {
                    eventViewModel.clearHighlight()
                },
                mapHeight = mapHeight,
                elevation = mapElevation,
                mapListToggle = mapListToggle,
                onToggle = {
                    mapListToggle = !mapListToggle
                           eventViewModel.clearHighlight()
                           },
                locations = eventFeatures,
                selectedEvent = selectedEvent,
                isSaved = isCurrentEventSaved,
                likedArtistNames = likedArtistNames,
                onDetailClick = {
                    mapListToggle = !mapListToggle
                    eventViewModel.clearHighlight()
                },
                onMarkerClick = { eventId ->
                    // Event anhand der ID (url) suchen
                    val event = eventViewModel.findEventById(eventId)
                    if (event != null) {
                        eventViewModel.highlightEvent(event)
                        eventViewModel.selectEvent(event)
                        // Kamera sofort setzen (ohne clearHighlight zu triggern)
                        coroutineScope.launch {
                            cameraState.animateTo(
                                CameraPosition(
                                    target = Position(event.longitude, event.latitude),
                                    zoom = 15.0
                                )
                            )
                        }
                        eventViewModel.onCameraTargetConsumed()
                    }
                }
            )

            EventContainer(
                events = events,
                selectedEvent = selectedEvent,
                eventSelected = eventSelected,
                listHeight = listHeight,
                elevation = listElevation,
                mapListToggle = mapListToggle,
                onToggle = {
                    mapListToggle = !mapListToggle
                    eventViewModel.clearHighlight()
                },
                onEventClick = { event ->
                    eventViewModel.selectEvent(event)

                    //eventViewModel.highlightEvent(event)
                    // MUSS NOCH RAUS
                },
                onBack = {
                    eventViewModel.clearSelection()
                    eventViewModel.clearHighlight()
                },
                // BUG FIX: Vorher `{ eventViewModel.saveEventToFavorites(eventDto = selectedEvent!!) }`
                // — das Lambda rief saveButtonclick nie auf (wurde als Objekt übergeben, nicht aufgerufen).
                // Toggle-Logik: bereits gespeicherte Events werden beim erneuten Tippen entfernt.
                saveButtonclick = {
                    if (isCurrentEventSaved) {
                        eventViewModel.removeEventFromFavorites(it.url)
                    } else {
                        eventViewModel.saveEventToFavorites(it)
                    }
                },
                isSaved = isCurrentEventSaved,
                likedArtistNames = likedArtistNames
            )
        }
    }
}
