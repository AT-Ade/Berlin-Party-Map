package com.example.berlinpartymap.ui.savedevents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun SavedEventsScreen(
    modifier: Modifier = Modifier,
    viewModel: SavedEventsViewModel = koinViewModel(),
    onEventClick: (String) -> Unit = {}
) {
    val savedEventsWithLineup by viewModel.savedEvents.collectAsState()

    // Sortierungsstate aus dem ViewModel laden
    val currentSortOrder by viewModel.currentSortOrder.collectAsState()

    // Zustand für das Öffnen/Schließen des Menüs
    var showSortMenu by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {

        // Kopfzeile mit Titel links und Sortier-Menü rechts
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

            // Box hält das Dropdown-Menü fest verankert am Button
            Box {
                IconButton(onClick = { showSortMenu = true }) {
                    Icon(
                        imageVector = Icons.Filled.Sort,
                        contentDescription = "Events sortieren",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                DropdownMenu(
                    expanded = showSortMenu,
                    onDismissRequest = { showSortMenu = false }
                ) {
                    // Option 1: Datum (Chronologisch)
                    DropdownMenuItem(
                        text = { Text("Nach Datum") },
                        onClick = {
                            viewModel.setSortOrder(SavedEventsSortOrder.DATE_ASC)
                            showSortMenu = false
                        },
                        trailingIcon = {
                            if (currentSortOrder == SavedEventsSortOrder.DATE_ASC) {
                                Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                    )

                    // Option 2: Preis aufsteigend
                    DropdownMenuItem(
                        text = { Text("Preis: Günstigste zuerst") },
                        onClick = {
                            viewModel.setSortOrder(SavedEventsSortOrder.PRICE_ASC)
                            showSortMenu = false
                        },
                        trailingIcon = {
                            if (currentSortOrder == SavedEventsSortOrder.PRICE_ASC) {
                                Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                    )

                    // Option 3: Preis absteigend
                    DropdownMenuItem(
                        text = { Text("Preis: Teuerste zuerst") },
                        onClick = {
                            viewModel.setSortOrder(SavedEventsSortOrder.PRICE_DESC)
                            showSortMenu = false
                        },
                        trailingIcon = {
                            if (currentSortOrder == SavedEventsSortOrder.PRICE_DESC) {
                                Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                    )

                    // Option 4: Lieblingskünstler zuerst
                    DropdownMenuItem(
                        text = { Text("Meiste Lieblings-Artists") },
                        onClick = {
                            viewModel.setSortOrder(SavedEventsSortOrder.LIKED_ARTISTS_DESC)
                            showSortMenu = false
                        },
                        trailingIcon = {
                            if (currentSortOrder == SavedEventsSortOrder.LIKED_ARTISTS_DESC) {
                                Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                    )
                }
            }
        }

        // Liste der gespeicherten Events
        if (savedEventsWithLineup.isEmpty()) {
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
                items(savedEventsWithLineup) { item ->
                    SavedEventListItem(
                        event = item.event,
                        onClick = { onEventClick(item.event.eventId) }
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SavedEventsScreenPreview() {
    SavedEventsScreen()
}