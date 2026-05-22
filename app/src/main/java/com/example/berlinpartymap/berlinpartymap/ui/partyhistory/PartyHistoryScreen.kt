package com.example.berlinpartymap.ui.partyhistory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.berlinpartymap.data.local.EventWithLineup
import com.example.berlinpartymap.ui.savedevents.SavedEventListItem
import org.koin.androidx.compose.koinViewModel

//import org.koin.androidx.compose.koinViewModel

// BottomSheet wurde gegenüber Dialog bevorzugt, weil:
// 1. Die Liste der Events potenziell lang sein kann → Bottom Sheet scrollt nativ
// 2. Material3-Best-Practice für Listen-basierte Aktionen auf Mobile
// 3. Weniger aufdringlich als ein modaler Dialog, lässt sich natürlich wegwischen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartyHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: PartyHistoryViewModel = koinViewModel(),
    // Callback wenn ein List-Item angetippt wird; übergibt die eventId für Navigation
    onEventClick: (String) -> Unit = {}
) {
    val visitedEvents by viewModel.visitedEvents.collectAsState()
    val pendingEvents by viewModel.pendingConfirmationEvents.collectAsState()
    val pendingCount by viewModel.pendingConfirmationCount.collectAsState()
    val showSheet by viewModel.showConfirmationSheet.collectAsState()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false // Partial Expand erlaubt für kurze Listen
    )

    // Bottom-Sheet automatisch öffnen, sobald unbestätigte Events vorhanden sind
    LaunchedEffect(pendingCount) {
        if (pendingCount > 0) {
            viewModel.openConfirmationSheet()
        }
    }

    // Bottom-Sheet automatisch schließen, wenn alle Events bestätigt wurden
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

        // -------- Kopfzeile mit Titel und Re-Open-Button --------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Besuchte Events",
                style = MaterialTheme.typography.titleLarge
            )

            // Button zum manuellen Wiederöffnen des Bestätigungs-Sheets
            // Zeigt Badge mit Anzahl unbestätigter Events, wenn welche vorhanden
            BadgedBox(
                badge = {
                    if (pendingCount > 0) {
                        Badge { Text(pendingCount.toString()) }
                    }
                }
            ) {
                IconButton(
                    onClick = { viewModel.openConfirmationSheet() },
                    enabled = pendingCount > 0
                ) {
                    Icon(
                        imageVector = Icons.Filled.HelpOutline,
                        contentDescription = "Anwesenheit bestätigen",
                        tint = if (pendingCount > 0) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
            }
        }

        // -------- Liste der besuchten Events --------
        if (visitedEvents.isEmpty()) {
            Text(
                text = "Noch keine besuchten Events.",
                modifier = Modifier.padding(top = 16.dp),
                fontStyle = FontStyle.Italic
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(visitedEvents) { item ->
                    SavedEventListItem(
                        event = item.event,
                        onClick = { onEventClick(item.event.eventId) }
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
        // Überschrift
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

        // Liste der unbestätigten vergangenen Events
        pendingEvents.forEach { item ->
            AttendanceConfirmationItem(
                eventWithLineup = item,
                onConfirm = { onConfirm(item.event.eventId) },
                onDeny = { onDeny(item.event.eventId) }
            )
        }

        // Schließen-Button am Ende der Liste
        Button(
            onClick = onClose,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray.copy(0.6f)
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

        // Haken-Button: Ja, ich war da
        IconButton(onClick = onConfirm) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Ja, ich war da",
                tint = Color(0xFF4CAF50), // Grün
                modifier = Modifier.size(32.dp)
            )
        }

        // Kreuz-Button: Nein, ich war nicht da → Event wird aus Favoriten entfernt
        IconButton(onClick = onDeny) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Nein, ich war nicht da",
                tint = Color(0xFFF44336), // Rot
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
