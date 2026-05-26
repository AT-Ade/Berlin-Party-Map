package com.example.berlinpartymap.ui.savedevents

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.berlinpartymap.R
import com.example.berlinpartymap.data.local.EventWithLineup
import com.example.berlinpartymap.ui.components.StyledIconButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

// GEÄNDERT: Signatur angepasst – empfängt jetzt eventId: String statt EventWithLineup direkt.
// Das ist analog zu HistoryDetailScreen und ermöglicht Navigation per SavedDetailRoute
// (NavController in AppStart). Das Event wird im Screen selbst aus dem ViewModel geladen.
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SavedEventDetailScreen(
    eventId: String,                   // NEU: ID statt Objekt (wie HistoryDetailScreen)
    viewModel: SavedEventsViewModel,
    onBack: () -> Unit
) {
    // Event aus dem kombinierten Flow aktiver Favoriten laden (aktive + vergangene)
    val activeEvents by viewModel.activeSavedEvents.collectAsState()
    val pastEvents by viewModel.pastSavedEvents.collectAsState()

    // Event aus aktiven oder vergangenen Favoriten suchen
    val eventWithLineup: EventWithLineup? = remember(activeEvents, pastEvents, eventId) {
        activeEvents.find { it.event.eventId == eventId }
            ?: pastEvents.find { it.event.eventId == eventId }
    }

    // Falls das Event nicht (mehr) gefunden wird, zurücknavigieren
    if (eventWithLineup == null) {
        onBack()
        return
    }

    val event = eventWithLineup.event
    val lineup = eventWithLineup.lineup

    // Lokaler State für das Popup und die Animationen
    var showDialog by remember { mutableStateOf(false) }
    var isRotated by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(true) } // Startet Gelb

    val coroutineScope = rememberCoroutineScope()

    // Rotations-Animation (0 bis 360 Grad)
    val rotation by animateFloatAsState(
        targetValue = if (isRotated) 360f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "ButtonRotation"
    )

    // Farbanimation (Gelb zu Grau)
    val animatedStarColor by animateColorAsState(
        targetValue = if (isFavorite) Color.Yellow else Color.Gray,
        animationSpec = tween(durationMillis = 300),
        label = "StarColor"
    )

    val zonedDateStartTime = ZonedDateTime.parse(event.startTime)
    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY) }
    val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm", Locale.GERMANY) }
    val startDateString = zonedDateStartTime.format(dateFormatter)
    val startTimeString = zonedDateStartTime.format(timeFormatter)
    val endTimeString = ZonedDateTime.parse(event.endTime).format(timeFormatter)

    // -------- Bestätigungs-Popup --------
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Event entfernen?") },
            text = { Text("Bist du dir sicher? Das Event wird aus deinen gespeicherten Events gelöscht.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        coroutineScope.launch {
                            // 1. Zweite Drehung starten & Farbe wird grau
                            isRotated = !isRotated
                            isFavorite = false

                            // 500ms warten, damit die Drehung flüssig durchläuft
                            delay(500)

                            // 2. Repository/ViewModel Logik mit der korrekten ID feuern
                            viewModel.removeFavorite(event.eventId)

                            // 3. Zurück zur Listenansicht springen
                            onBack()
                        }
                    }
                ) {
                    Text("Ja, löschen", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Abbrechen")
                }
            }
        )
    }

    Column {
        // -------- Kopfzeile --------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StyledIconButton(
                onClick = onBack,
                icon = Icons.Rounded.ArrowBackIosNew,
                iconColor = Color.White
            )

            // Animierter Favoriten-Button
            StyledIconButton(
                modifier = Modifier.rotate(rotation),
                onClick = {
                    isRotated = !isRotated // Erste Drehung beim Click
                    showDialog = true      // Dialog zeigen
                },
                icon = Icons.Rounded.Star,
                iconColor = animatedStarColor
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            // -------- Flyer --------
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = event.flyerURL,
                        contentDescription = "${event.name} Flyer",
                        error = painterResource(R.drawable.placeholderevent),
                        modifier = Modifier
                            .padding(16.dp)
                            .size(180.dp),
                        contentScale = ContentScale.FillHeight
                    )
                }
            }

            // -------- Info-Block --------
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        event.name,
                        color = Color.LightGray,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )

                    Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.CalendarToday, "Datum", tint = Color.White, modifier = Modifier.size(25.dp))
                        Text(startDateString, color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(horizontal = 8.dp))
                    }

                    Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.AccessTime, "Uhrzeit", tint = Color.White, modifier = Modifier.size(25.dp))
                        Text("$startTimeString - $endTimeString", color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(horizontal = 8.dp))
                    }

                    Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.LocationOn, "Ort", tint = Color.White, modifier = Modifier.size(25.dp))
                        Text(event.venueName, color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(horizontal = 8.dp))
                    }

                    Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Euro, "Preis", tint = Color.White, modifier = Modifier.size(25.dp))
                        Text(
                            if (event.price != 0.0) "${event.price.roundToInt()}€" else "free",
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }

            // -------- Lineup --------
            item {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text("LINEUP", color = Color.LightGray, fontWeight = FontWeight.Bold, fontSize = 25.sp, fontStyle = FontStyle.Italic)
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        lineup.forEach { artist ->
                            val borderColor = if (artist.iLike) Color.Red else Color.White
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent, contentColor = Color.White),
                                modifier = Modifier.padding(4.dp).border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(10.dp))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(artist.name)
                                    if (artist.iLike) {
                                        Icon(Icons.Filled.Favorite, "Liked", tint = Color.Red, modifier = Modifier.size(14.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // -------- Beschreibung --------
            item {
                Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Text("BESCHREIBUNG", color = Color.LightGray, fontWeight = FontWeight.Bold, fontSize = 25.sp, fontStyle = FontStyle.Italic)
                    Text(event.description, color = Color.White)
                }
            }
        }
    }
}
