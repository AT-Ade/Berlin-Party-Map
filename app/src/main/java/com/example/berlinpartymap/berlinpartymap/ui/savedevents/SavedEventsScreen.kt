package com.example.berlinpartymap.ui.savedevents

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

// GEÄNDERT: onEventClick-Callback hinzugefügt, damit die Navigation per NavController
// aus AppStart heraus gesteuert wird – analog zu PartyHistoryScreen und EventContainer.
// Die interne AnimatedContent-Navigation (eventSelected / selectedEvent im ViewModel)
// wurde entfernt, da sie durch die Route-basierte Navigation ersetzt wird.
@Composable
fun SavedEventsScreen(
    modifier: Modifier = Modifier,
    viewModel: SavedEventsViewModel = koinViewModel(),
    onEventClick: (String) -> Unit = {}  // NEU: Navigations-Callback mit eventId
) {
    val activeEvents by viewModel.activeSavedEvents.collectAsState()
    val pastEvents by viewModel.pastSavedEvents.collectAsState()

    var showPastEvents by remember { mutableStateOf(false) }

    val arrowRotation by animateFloatAsState(
        targetValue = if (showPastEvents) 180f else 0f,
        label = "ArrowRotation"
    )

    // -------- LISTE DER EVENTS --------
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Meine gespeicherten Events",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )

        if (activeEvents.isEmpty() && pastEvents.isEmpty()) {
            Text(
                text = "Noch keine Events gespeichert.",
                modifier = Modifier.padding(top = 16.dp),
                fontStyle = FontStyle.Italic
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (activeEvents.isEmpty() && pastEvents.isNotEmpty()) {
                    item {
                        Text(
                            text = "Keine anstehenden Events.",
                            style = MaterialTheme.typography.bodyMedium,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                } else {
                    items(activeEvents, key = { it.event.eventId }) { item ->
                        SavedEventListItem(
                            event = item.event,
                            // GEÄNDERT: Navigation per Callback nach oben reichen (wie PartyHistoryScreen)
                            onClick = { onEventClick(item.event.eventId) }
                        )
                    }
                }

                if (pastEvents.isNotEmpty()) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(
                                onClick = { showPastEvents = !showPastEvents },
                                colors = ButtonDefaults.textButtonColors(contentColor = Color.LightGray)
                            ) {
                                Text(
                                    text = if (showPastEvents) "Vergangene Events ausblenden"
                                    else "Vergangene Events anzeigen (${pastEvents.size})"
                                )
                                Icon(
                                    imageVector = Icons.Default.ExpandMore,
                                    contentDescription = "Ausklappen",
                                    modifier = Modifier.padding(start = 4.dp).rotate(arrowRotation)
                                )
                            }
                        }
                    }

                    if (showPastEvents) {
                        items(pastEvents, key = { "past_${it.event.eventId}" }) { item ->
                            SavedEventListItem(
                                event = item.event,
                                // GEÄNDERT: Navigation per Callback nach oben reichen (wie PartyHistoryScreen)
                                onClick = { onEventClick(item.event.eventId) }
                            )
                        }
                    }
                }
            }
        }
    }
}
