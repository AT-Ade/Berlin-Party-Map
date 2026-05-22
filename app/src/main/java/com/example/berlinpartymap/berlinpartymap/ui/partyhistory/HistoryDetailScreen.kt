package com.example.berlinpartymap.ui.partyhistory

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Euro
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.berlinpartymap.ui.components.StyledIconButton
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

// Detail-Ansicht für ein besuchtes Event aus der History
@Composable
fun HistoryDetailScreen(
    eventId: String,
    viewModel: PartyHistoryViewModel,
    onBack: () -> Unit
) {
    // Event aus dem StateFlow der besuchten Events laden
    val visitedEvents by viewModel.visitedEvents.collectAsState()
    val item = visitedEvents.find { it.event.eventId == eventId }

    // Falls das Event nicht mehr vorhanden ist, zurücknavigieren
    if (item == null) {
        onBack()
        return
    }

    val event = item.event
    val lineup = item.lineup

    // Datums- und Zeitformatierung
    val zonedDateStartTime = ZonedDateTime.parse(event.startTime)
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY)
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.GERMANY)
    val startDateString = zonedDateStartTime.format(dateFormatter)
    val startTimeString = zonedDateStartTime.format(timeFormatter)
    val endTimeString = ZonedDateTime.parse(event.endTime).format(timeFormatter)

    Column {
        // -------- Kopfzeile: Zurück-Button --------
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
                    Row(
                        Modifier.fillMaxWidth().padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.CalendarToday, "Datum", tint = Color.White, modifier = Modifier.size(25.dp))
                        Text(startDateString, color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(horizontal = 8.dp))
                    }
                    Row(
                        Modifier.fillMaxWidth().padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.AccessTime, "Uhrzeit", tint = Color.White, modifier = Modifier.size(25.dp))
                        Text("$startTimeString - $endTimeString", color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(horizontal = 8.dp))
                    }
                    Row(
                        Modifier.fillMaxWidth().padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.LocationOn, "Ort", tint = Color.White, modifier = Modifier.size(25.dp))
                        Text(event.venueName, color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(horizontal = 8.dp))
                    }
                    Row(
                        Modifier.fillMaxWidth().padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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

            // -------- Sternebewertung (1-5) --------
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        "BEWERTUNG",
                        color = Color.LightGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        fontStyle = FontStyle.Italic
                    )
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // 5 Sterne-Icons; ausgefüllter Stern wenn Rating >= i
                        (1..5).forEach { starIndex ->
                            val isFilled = (event.rating ?: 0) >= starIndex
                            Icon(
                                imageVector = if (isFilled) Icons.Filled.Star else Icons.Outlined.StarOutline,
                                contentDescription = "$starIndex Stern",
                                tint = if (isFilled) Color(0xFFFFD700) else Color.Gray, // Gold / Grau
                                modifier = Modifier
                                    .size(40.dp)
                                    // Tipp auf einen Stern speichert die Bewertung
                                    .clickable { viewModel.updateRating(event.eventId, starIndex) }
                            )
                        }
                    }
                }
            }

            // -------- Lineup mit Like-Funktion --------
            // Bereits gelikte Artists (aus Favoriten-Detail) erscheinen hier ebenfalls markiert
            item {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        "LINEUP",
                        color = Color.LightGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        fontStyle = FontStyle.Italic
                    )
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        lineup.forEach { artist ->
                            val borderColor = if (artist.iLike) Color.Red else Color.White

                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.Transparent,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .border(
                                        width = 1.dp,
                                        color = borderColor,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    // Tipp auf Artist-Chip toggled den Like-Status
                                    .clickable {
                                        viewModel.toggleArtistLike(artist.artistId, artist.iLike)
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(artist.name)
                                    // Herz-Icon erscheint nur für gelikte Artists
                                    if (artist.iLike) {
                                        Icon(
                                            imageVector = Icons.Filled.Favorite,
                                            contentDescription = "Liked",
                                            tint = Color.Red,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // -------- Beschreibung --------
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        "BESCHREIBUNG",
                        color = Color.LightGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        fontStyle = FontStyle.Italic
                    )
                    Text(event.description, color = Color.White)
                }
            }
        }
    }
}
