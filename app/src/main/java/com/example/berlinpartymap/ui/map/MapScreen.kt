package com.example.berlinpartymap.ui.map

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.berlinpartymap.ui.components.Background
import kotlinx.coroutines.launch
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.spatialk.geojson.Position

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    mapViewModel: MapViewModel = viewModel()
) {
    val events by mapViewModel.events.collectAsState()
    val eventFeatures by mapViewModel.eventFeatures.collectAsState()
    val selectedEvent by mapViewModel.selectedEvent.collectAsState()
    val highlightedEvent by mapViewModel.highlightedEvent.collectAsState()
    val eventSelected by mapViewModel.eventSelected.collectAsState()
    val cameraTarget by mapViewModel.cameraTarget.collectAsState()

    var mapListToggle by remember { mutableStateOf(true) }

    // -------- Animationen --------
    val mapHeight by animateDpAsState(
        targetValue = if (mapListToggle) 600.dp else 200.dp,
        animationSpec = tween(200), label = ""
    )
    val listHeight by animateDpAsState(
        targetValue = if (mapListToggle) 200.dp else 600.dp,
        animationSpec = tween(200), label = ""
    )
    val mapElevation by animateDpAsState(
        targetValue = if (mapListToggle) 20.dp else 5.dp, label = ""
    )
    val listElevation by animateDpAsState(
        targetValue = if (!mapListToggle) 20.dp else 5.dp, label = ""
    )

    // Back Navigation für Detail View
    if (selectedEvent != null) {
        BackHandler { mapViewModel.clearSelection() }
    }

    // -------- MapLibre Kamera --------
    // cameraState wird hier erstellt und an MapContainer weitergegeben,
    // damit wir von überall (EventListe, Pin-Klick) die Kamera steuern können.
    val cameraState = rememberCameraState(
        CameraPosition(
            target = Position(13.4050, 52.5200),
            zoom = 11.0
        )
    )

    val coroutineScope = rememberCoroutineScope()

    // Reagiert auf Kamera-Ziel aus dem ViewModel (z.B. wenn aus der Liste ein Event geöffnet wird)
    LaunchedEffect(cameraTarget) {
        val target = cameraTarget ?: return@LaunchedEffect
        coroutineScope.launch {
            cameraState.animateTo(
                CameraPosition(
                    target = Position(target.first, target.second),
                    zoom = 15.0
                )
            )
        }
        mapViewModel.onCameraTargetConsumed()
    }

    // -------- Daten laden --------
    LaunchedEffect(Unit) {
        mapViewModel.loadInitialData()
    }

    // -------- UI --------
    Box {
        Background()

        Column {
            MapContainer(
                cameraState = cameraState,
                highlightedEvent = highlightedEvent,
                onCloseInfo = {
                    mapViewModel.clearHighlight()
                },
                mapHeight = mapHeight,
                elevation = mapElevation,
                mapListToggle = mapListToggle,
                onToggle = { mapListToggle = !mapListToggle },
                locations = eventFeatures,
                onMarkerClick = { eventId ->
                    // Event anhand der ID (url) suchen
                    val event = mapViewModel.findEventById(eventId)
                    if (event != null) {
                        mapViewModel.highlightEvent(event)
                        // Kamera sofort setzen (ohne clearHighlight zu triggern)
                        coroutineScope.launch {
                            cameraState.animateTo(
                                CameraPosition(
                                    target = Position(event.longitude, event.latitude),
                                    zoom = 15.0
                                )
                            )
                        }
                        mapViewModel.onCameraTargetConsumed()
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
                    mapViewModel.selectEvent(event)
                    mapViewModel.highlightEvent(event)
                    // highlightEvent setzt cameraTarget → LaunchedEffect übernimmt den Sprung
                },
                onBack = {
                    mapViewModel.clearSelection()
                    mapViewModel.clearHighlight()
                }
            )
        }
    }
}
