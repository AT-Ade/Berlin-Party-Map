package com.example.berlinpartymap.ui.map

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.berlinpartymap.ui.components.Background
import com.example.berlinpartymap.ui.savedevents.SavedEventsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.spatialk.geojson.Position
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
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
    val selectedDate by eventViewModel.selectedDate.collectAsState()
    val uiState by eventViewModel.uiState.collectAsState()

    val savedEventsWithLineup by savedEventsViewModel.savedEvents.collectAsState()
    var mapListToggle by remember { mutableStateOf(true) }
    var showDatePicker by remember { mutableStateOf(false) }

    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY) }

    // -------- OPTIMIERTE ANIMATIONEN (Weight statt feste Höhen) --------
    val mapWeight by animateFloatAsState(
        targetValue = if (mapListToggle) 3f else 1f,
        animationSpec = tween(250, easing = FastOutSlowInEasing),
        label = "MapWeightAnimation"
    )
    val listWeight by animateFloatAsState(
        targetValue = if (mapListToggle) 1f else 3f,
        animationSpec = tween(250, easing = FastOutSlowInEasing),
        label = "ListWeightAnimation"
    )

    val mapElevation by animateDpAsState(targetValue = if (mapListToggle) 20.dp else 5.dp, label = "MapElevation")
    val listElevation by animateDpAsState(targetValue = if (!mapListToggle) 20.dp else 5.dp, label = "ListElevation")

    if (selectedEvent != null) {
        BackHandler { eventViewModel.clearSelection() }
    }

    // -------- MapLibre Kamera --------
    val cameraState = rememberCameraState(
        CameraPosition(target = Position(13.4050, 52.5200), zoom = 11.0)
    )
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(cameraTarget) {
        cameraTarget?.let { target ->
            cameraState.animateTo(
                CameraPosition(target = Position(target.first, target.second), zoom = 15.0)
            )
            eventViewModel.onCameraTargetConsumed()
        }
    }

    // -------- Daten laden --------
    LaunchedEffect(Unit) {
        eventViewModel.loadInitialData()
    }

    // -------- OPTIMIERUNG: String-Vergleiche gecacht mittels remember --------
    val isCurrentEventSaved = remember(savedEventsWithLineup, selectedEvent) {
        savedEventsWithLineup.any {
            it.event.eventId.trim().lowercase() == selectedEvent?.url?.trim()?.lowercase()
        }
    }

    val likedArtistNames: Set<String> by remember(savedEventsWithLineup) {
        derivedStateOf {
            savedEventsWithLineup
                .flatMap { eventWithLineup -> eventWithLineup.lineup }
                .filter { artistEntity -> artistEntity.iLike }
                .map { artistEntity -> artistEntity.name }
                .toSet()
        }
    }

    // -------- UI --------
    Box(modifier = Modifier.fillMaxSize()) {
        Background()

        Column(
            modifier = Modifier.fillMaxSize().padding(vertical = 4.dp),
            verticalArrangement = Arrangement.Top
        ) {
            // MapContainer – mapHeight wurde entfernt
            MapContainer(
                cameraState = cameraState,
                highlightedEvent = highlightedEvent,
                onCloseInfo = {
                    eventViewModel.clearHighlight()
                    eventViewModel.clearSelection()
                },
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
                    val event = eventViewModel.findEventById(eventId)
                    if (event != null) {
                        eventViewModel.highlightEvent(event)
                        eventViewModel.selectEvent(event)
                        coroutineScope.launch {
                            cameraState.animateTo(
                                CameraPosition(target = Position(event.longitude, event.latitude), zoom = 15.0)
                            )
                        }
                        eventViewModel.onCameraTargetConsumed()
                    }
                },
                modifier = Modifier
                    .weight(mapWeight)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            // EventContainer – listHeight wurde entfernt
            EventContainer(
                events = events,
                uiState = uiState,
                formattedDate = selectedDate.format(dateFormatter),
                onPrevDate = { eventViewModel.changeDate(-1) },
                onNextDate = { eventViewModel.changeDate(1) },
                onDatePickerClick = { showDatePicker = true },
                onRetry = { eventViewModel.loadEventsForSelectedDate() },
                selectedEvent = selectedEvent,
                eventSelected = eventSelected,
                elevation = listElevation,
                mapListToggle = mapListToggle,
                onToggle = {
                    mapListToggle = !mapListToggle
                    eventViewModel.clearHighlight()
                },
                onEventClick = { event -> eventViewModel.selectEvent(event) },
                onBack = {
                    eventViewModel.clearSelection()
                    eventViewModel.clearHighlight()
                },
                saveButtonclick = {
                    if (isCurrentEventSaved) {
                        eventViewModel.removeEventFromFavorites(it.url)
                    } else {
                        eventViewModel.saveEventToFavorites(it)
                    }
                },
                isSaved = isCurrentEventSaved,
                likedArtistNames = likedArtistNames,
                modifier = Modifier
                    .weight(listWeight)
                    .fillMaxWidth()
            )
        }

        // ------------- MATERIAL 3 DATE PICKER DIALOG -------------
        if (showDatePicker) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            )

            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                val localDate = Instant.ofEpochMilli(millis)
                                    .atZone(ZoneId.of("UTC"))
                                    .toLocalDate()
                                eventViewModel.setSpecificDate(localDate)
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("Auswählen")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Abbrechen")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}