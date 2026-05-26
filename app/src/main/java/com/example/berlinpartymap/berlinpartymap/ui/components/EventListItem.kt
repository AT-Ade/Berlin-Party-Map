package com.example.berlinpartymap.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.berlinpartymap.R
import com.example.berlinpartymap.data.remote.dto.ArtistDto
import com.example.berlinpartymap.data.remote.dto.EventDto
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun EventListItem(
    modifier: Modifier = Modifier,
    event: EventDto,
    likedArtistsCount: Int = 0, // NEU: Parameter für die Anzahl gelikter Künstler
    onClick: () -> Unit,
) {
    val zonedDateStartTime = ZonedDateTime.parse(event.startTime)
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY)
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.GERMANY)

    val startDateString = zonedDateStartTime.format(dateFormatter)
    val startTimeString = zonedDateStartTime.format(timeFormatter)

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(), // Modifier von außen erlauben
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray.copy(0.3f),
            contentColor = Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = event.flyerURL,
                contentDescription = "${event.name} Flyer",
                error = painterResource(R.drawable.placeholderevent),
                modifier = Modifier
                    .padding(16.dp)
                    .size(60.dp),
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier.padding(8.dp).weight(1f)
            ) {
                Text(event.venueName, fontWeight = FontWeight.Bold)
                Text(event.name)

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(startDateString, fontWeight = FontWeight.Bold)
                    Text(" | $startTimeString Uhr | ", fontWeight = FontWeight.Bold)
                    Text(
                        if (event.price != 0.0) "${event.price.roundToInt()}€" else "free",
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )

                }

            }
            // NEU: Wenn gelikte Artists im Lineup sind, zeigen wir ein kleines Badge/Icon an
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(4.dp)
            ){
                if (likedArtistsCount > 0) {
                    //Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Gelikter Artist",
                        tint = Color(0xFFE91E63), // Schönes Pink/Rot für das Herz
                        modifier = Modifier.size(28.dp)
                    )
                    //Spacer(modifier = Modifier.width(4.dp))
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
private fun EventListItemPreview() {
    EventListItem(
        event = EventDto(
            name = "Party Berlin",
            venueName = "Club Berlin",
            latitude = 52.4837,
            longitude = 13.4482,
            startTime = "2026-04-24T16:00:00+02:00",
            endTime = "2026-04-25T08:00:00+02:00",
            lineup = listOf(ArtistDto("CRYME")),
            description = "description",
            url = "https://de.ra.co/events/2385678",
            flyerURL = "",
            venueAddress = "Clubstraße 123",
            price = 25.0
        ),
        likedArtistsCount = 3, // In der Preview testen
        onClick = {}
    )
}