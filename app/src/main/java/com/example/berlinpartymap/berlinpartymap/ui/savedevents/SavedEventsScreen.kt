package com.example.berlinpartymap.ui.savedevents

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

// Navigation per NavController-Callback (analog zu PartyHistoryScreen)
@Composable
fun SavedEventsScreen(
    modifier: Modifier = Modifier,
    viewModel: SavedEventsViewModel = koinViewModel(),
    onEventClick: (String) -> Unit = {}
) {
    val activeEvents by viewModel.activeSavedEvents.collectAsState()
    val pastEvents by viewModel.pastSavedEvents.collectAsState()
    val currentSortOrder by viewModel.currentSortOrder.collectAsState()
    val likedArtistNames by viewModel.likedArtistNames.collectAsState()

    var showPastEvents by remember { mutableStateOf(false) }
    // Steuert ob das Sortier-Dropdown offen ist – analog zu PartyHistoryScreen
    var showSortMenu by remember { mutableStateOf(false) }

    val arrowRotation by animateFloatAsState(
        targetValue = if (showPastEvents) 180f else 0f,
        label = "ArrowRotation"
    )

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {

        // -------- Kopfzeile mit Titel und Sortier-Dropdown – analog zu PartyHistoryScreen --------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Meine gespeicherten Events",
                style = MaterialTheme.typography.titleLarge
            )

            // Box hält das Dropdown an der richtigen Position
            Box {
                IconButton(onClick = { showSortMenu = true }) {
                    Icon(
                        imageVector = Icons.Filled.Sort,
                        contentDescription = "Sortieren",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                DropdownMenu(
                    expanded = showSortMenu,
                    onDismissRequest = { showSortMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Datum aufsteigend") },
                        onClick = {
                            viewModel.setSortOrder(FavoritesSortOrder.DATE_ASC)
                            showSortMenu = false
                        },
                        trailingIcon = {
                            if (currentSortOrder == FavoritesSortOrder.DATE_ASC) {
                                Icon(Icons.Filled.Check, contentDescription = null)
                            }
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Datum absteigend") },
                        onClick = {
                            viewModel.setSortOrder(FavoritesSortOrder.DATE_DESC)
                            showSortMenu = false
                        },
                        trailingIcon = {
                            if (currentSortOrder == FavoritesSortOrder.DATE_DESC) {
                                Icon(Icons.Filled.Check, contentDescription = null)
                            }
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Preis aufsteigend") },
                        onClick = {
                            viewModel.setSortOrder(FavoritesSortOrder.PRICE_ASC)
                            showSortMenu = false
                        },
                        trailingIcon = {
                            if (currentSortOrder == FavoritesSortOrder.PRICE_ASC) {
                                Icon(Icons.Filled.Check, contentDescription = null)
                            }
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Preis absteigend") },
                        onClick = {
                            viewModel.setSortOrder(FavoritesSortOrder.PRICE_DESC)
                            showSortMenu = false
                        },
                        trailingIcon = {
                            if (currentSortOrder == FavoritesSortOrder.PRICE_DESC) {
                                Icon(Icons.Filled.Check, contentDescription = null)
                            }
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Gelikte Artists") },
                        onClick = {
                            viewModel.setSortOrder(FavoritesSortOrder.LIKED_ARTISTS)
                            showSortMenu = false
                        },
                        trailingIcon = {
                            if (currentSortOrder == FavoritesSortOrder.LIKED_ARTISTS) {
                                Icon(Icons.Filled.Check, contentDescription = null)
                            }
                        }
                    )
                }
            }
        }

        // -------- Liste --------
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
                            likedArtistsCount = item.lineup.count { likedArtistNames.contains(it.name) },
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
                                likedArtistsCount = item.lineup.count { likedArtistNames.contains(it.name) },
                                onClick = { onEventClick(item.event.eventId) }
                            )
                        }
                    }
                }
            }
        }
    }
}
