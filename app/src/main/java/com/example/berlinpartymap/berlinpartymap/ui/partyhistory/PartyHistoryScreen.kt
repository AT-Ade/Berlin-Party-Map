package com.example.berlinpartymap.ui.partyhistory

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.berlinpartymap.data.local.EventWithLineup
import com.example.berlinpartymap.ui.savedevents.SavedEventListItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartyHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: PartyHistoryViewModel = koinViewModel(),
    onEventClick: (String) -> Unit = {}
) {
    val visitedEvents by viewModel.visitedEvents.collectAsState()
    val pendingEvents by viewModel.pendingConfirmationEvents.collectAsState()
    val pendingCount by viewModel.pendingConfirmationCount.collectAsState()
    val showSheet by viewModel.showConfirmationSheet.collectAsState()

    // Aktuelle Sortierung aus dem ViewModel
    val currentSortOrder by viewModel.currentSortOrder.collectAsState()

    // Zustand, ob das Dropdown-Menü gerade geöffnet ist
    var showSortMenu by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    LaunchedEffect(pendingEvents) {
        if (pendingEvents.isEmpty() && showSheet) {
            viewModel.closeConfirmationSheet()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // -------- Kopfzeile mit Titel und Dropdown-Sortier-Button --------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Besuchte Events",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            // Box hält das Dropdown-Menü an der richtigen Position fixiert
            Box {
                IconButton(onClick = { showSortMenu = true }) {
                    Icon(
                        imageVector = Icons.Filled.Sort,
                        contentDescription = "Sortieren",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                // Das eigentliche Dropdown-Menü
                DropdownMenu(
                    expanded = showSortMenu,
                    onDismissRequest = { showSortMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Neueste zuerst") },
                        onClick = {
                            viewModel.setSortOrder(HistorySortOrder.DATE_DESC)
                            showSortMenu = false
                        },
                        trailingIcon = {
                            if (currentSortOrder == HistorySortOrder.DATE_DESC) {
                                Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Älteste zuerst") },
                        onClick = {
                            viewModel.setSortOrder(HistorySortOrder.DATE_ASC)
                            showSortMenu = false
                        },
                        trailingIcon = {
                            if (currentSortOrder == HistorySortOrder.DATE_ASC) {
                                Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Beste Bewertung") },
                        onClick = {
                            viewModel.setSortOrder(HistorySortOrder.RATING_DESC)
                            showSortMenu = false
                        },
                        trailingIcon = {
                            if (currentSortOrder == HistorySortOrder.RATING_DESC) {
                                Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Niedrigste Bewertung") },
                        onClick = {
                            viewModel.setSortOrder(HistorySortOrder.RATING_ASC)
                            showSortMenu = false
                        },
                        trailingIcon = {
                            if (currentSortOrder == HistorySortOrder.RATING_ASC) {
                                Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                    )

                    // -------- HIER IST DER NEUE EINTRAG --------
                    DropdownMenuItem(
                        text = { Text("Gelikete Artists zuerst") },
                        onClick = {
                            viewModel.setSortOrder(HistorySortOrder.LIKED_ARTISTS)
                            showSortMenu = false
                        },
                        trailingIcon = {
                            if (currentSortOrder == HistorySortOrder.LIKED_ARTISTS) {
                                Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                    )
                }
            }
        }

        // -------- Liste der besuchten Events --------
        if (visitedEvents.isEmpty()) {
            Text(
                text = "Noch keine besuchten Events.",
                modifier = Modifier.padding(top = 16.dp).weight(1f),
                fontStyle = FontStyle.Italic,
                color = Color.White
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(visitedEvents) { item ->
                    HistoryEventListItem(
                        event = item.event,
                        onClick = { onEventClick(item.event.eventId) },
                        // Zählt alle Künstler im Lineup dieses Events, bei denen iLike == true ist
                        likedArtistsCount = item.lineup.count { it.iLike }
                    )
                }
            }
        }

        // -------------- Banner mit Button um Bestätigung einzufordern ---------------
        if (pendingEvents.isNotEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text(
                    "${pendingCount} gespeichertes Event vergangen. Warst du da?",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Button(
                    onClick = { viewModel.openConfirmationSheet() },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    shape = CircleShape,
                    border = BorderStroke(width = 1.dp, color = Color.White),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray.copy(0.7f),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        "Jetzt bestätigen",
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    // -------- Bottom-Sheet: Anwesenheitsbestätigung --------
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.closeConfirmationSheet() },
            sheetState = sheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            AttendanceConfirmationSheetContent(
                pendingEvents = pendingEvents,
                onConfirm = { viewModel.confirmAttendance(it) },
                onDeny = { viewModel.denyAttendance(it) },
                onClose = { viewModel.closeConfirmationSheet() }
            )
        }
    }
}

// -------- Inhalt des Bottom-Sheets --------
@Composable
private fun AttendanceConfirmationSheetContent(
    pendingEvents: List<EventWithLineup>,
    onConfirm: (String) -> Unit,
    onDeny: (String) -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Warst du dabei?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Bestätige, bei welchen Events du dabei warst.",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        pendingEvents.forEach { item ->
            AttendanceConfirmationItem(
                eventWithLineup = item,
                onConfirm = { onConfirm(item.event.eventId) },
                onDeny = { onDeny(item.event.eventId) }
            )
        }

        Button(
            onClick = onClose,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            shape = CircleShape,
            border = BorderStroke(width = 1.dp, color = Color.White),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray.copy(0.7f),
                contentColor = Color.White
            )
        ) {
            Text("Später")
        }
    }
}

// -------- Einzelnes Event im Bottom-Sheet --------
@Composable
private fun AttendanceConfirmationItem(
    eventWithLineup: EventWithLineup,
    onConfirm: () -> Unit,
    onDeny: () -> Unit
) {
    val event = eventWithLineup.event

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(event.venueName, fontWeight = FontWeight.Bold)
            Text(event.name, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = onConfirm) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Ja, ich war da",
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(32.dp)
            )
        }

        IconButton(onClick = onDeny) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Nein, ich war nicht da",
                tint = Color(0xFFF44336),
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PartyHistoryScreenScreenPreview() {
    PartyHistoryScreen()
}