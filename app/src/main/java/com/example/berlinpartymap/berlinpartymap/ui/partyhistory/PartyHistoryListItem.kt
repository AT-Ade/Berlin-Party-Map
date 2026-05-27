package com.example.berlinpartymap.ui.partyhistory

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.berlinpartymap.R
import com.example.berlinpartymap.data.local.EventEntity
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun HistoryEventListItem(
    modifier: Modifier = Modifier,
    event: EventEntity,
    likedArtistsCount: Int,
    onClick: () -> Unit,
) {
    val zonedDateStartTime = ZonedDateTime.parse(event.startTime)
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY)
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.GERMANY)

    val startDateString = zonedDateStartTime.format(dateFormatter)
    val startTimeString = zonedDateStartTime.format(timeFormatter)

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray.copy(0.6f),
            contentColor = Color.White
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray.copy(0.3f))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // -------- Flyer --------
            AsyncImage(
                model = event.flyerURL,
                contentDescription = "${event.name} Flyer",
                error = painterResource(R.drawable.placeholderevent),
                modifier = Modifier
                    .padding(16.dp)
                    .size(60.dp),
                contentScale = ContentScale.FillHeight
            )

            // -------- Details --------
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .height(130.dp)
                    .weight(1f) // Nimmt den verfügbaren Platz ein, damit die Badges rechts stehen
            ) {
                Text(event.venueName, fontWeight = FontWeight.Bold)
                Text(event.name,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                    )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(startDateString, fontWeight = FontWeight.Bold)
                    Text(" | $startTimeString Uhr | ", fontWeight = FontWeight.Bold)
                    Text(
                        if (event.price != 0.0) "${event.price.roundToInt()}€" else "free",
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )
                }

                // -------- Sternebewertung --------
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp)
                ) {
                    val currentRating = event.rating ?: 0
                    (1..5).forEach { starIndex ->
                        val isFilled = currentRating >= starIndex
                        Icon(
                            imageVector = if (isFilled) Icons.Filled.Star else Icons.Outlined.StarOutline,
                            contentDescription = "$starIndex Sterne",
                            tint = if (isFilled) Color(0xFFFFD700) else Color.Gray,
                            modifier = Modifier.size(16.dp) // Kleinere Sterne für die Listenansicht
                        )
                    }
                }
            }

            // -------- Liked Artists Badge --------
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                if (likedArtistsCount > 0) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Gelikter Artist",
                        tint = Color(0xFFE91E63),
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        text = "$likedArtistsCount",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryEventListItemPreview() {
    HistoryEventListItem(
        event = EventEntity(
            name = "Sisyphos Nacht",
            venueName = "Sisyphos",
            latitude = 52.4837,
            longitude = 13.4482,
            startTime = "2026-04-24T23:00:00+02:00",
            endTime = "2026-04-25T10:00:00+02:00",
            description = "Tolle Party!",
            eventId = "123",
            flyerURL = "",
            venueAddress = "Hauptstraße 1",
            price = 20.0,
            rating = 4 // Test-Rating für die Preview
        ),
        onClick = {},
        likedArtistsCount = 3
    )
}