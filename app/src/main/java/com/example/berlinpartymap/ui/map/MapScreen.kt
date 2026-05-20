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
                onToggle = { mapListToggle = !mapListToggle },
                locations = eventFeatures,
                onMarkerClick = { eventId ->
                    // Event anhand der ID (url) suchen
                    val event = eventViewModel.findEventById(eventId)
                    if (event != null) {
                        eventViewModel.highlightEvent(event)
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
                onToggle = { mapListToggle = !mapListToggle },
                onEventClick = { event ->
                    eventViewModel.selectEvent(event)

                    //eventViewModel.highlightEvent(event)
                    // MUSS NOCH RAUS
                },
                onBack = {
                    eventViewModel.clearSelection()
                    eventViewModel.clearHighlight()
                },
                saveButtonclick = {eventViewModel.saveEventToFavorites(eventDto = selectedEvent!!)},
                isSaved = savedEventsWithLineup.any { it.event.eventId == selectedEvent?.url }            )
        }
    }
}
