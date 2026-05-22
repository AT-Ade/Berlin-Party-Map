package com.example.berlinpartymap.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    onClick: () -> Unit,
) {

    val zonedDateStartTime = ZonedDateTime.parse(event.startTime)
    // Format für das Datum (25.04.2026)
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY)
    // Format für die Uhrzeit (23:00)
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.GERMANY)
    // Anwendung:
    val startDateString = zonedDateStartTime.format(dateFormatter) // -> Format : "25.04.2026"
    val startTimeString = zonedDateStartTime.format(timeFormatter) // -> Format : "23:00"

    val zonedDateEndTime = ZonedDateTime.parse(event.endTime)
    val endDateString = zonedDateEndTime.format(dateFormatter)
    val endTimeString = zonedDateEndTime.format(timeFormatter)

    Card(
        onClick = onClick,
        modifier = Modifier
            //.padding(start = 16.dp, end = 16.dp, top = 8.dp)
            .fillMaxWidth(1f),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray.copy(0.3f),
            contentColor = Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                model = event.flyerURL,
                contentDescription = "${event.name} Flyer",
                //placeholder = painterResource(R.drawable.placeholderevent),
                error = painterResource(R.drawable.placeholderevent),
                modifier = Modifier
                    .padding(16.dp)
                    .size(60.dp),
                    //.clip(CircleShape),
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(event.venueName, fontWeight = FontWeight.Bold)
                Text(event.name)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    //horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(startDateString, fontWeight = FontWeight.Bold)
                    Text(" | $startTimeString Uhr | ", fontWeight = FontWeight.Bold)
                    Text(
                        if(event.price != 0.0) "${event.price.roundToInt()}€" else "free",
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EventListItemPreview() {
    // Use Theme here
    EventListItem(
        event = EventDto(
            name = "Party Berlin",
            venueName = "Club Berlin",
            latitude = 52.4837,
            longitude = 13.4482,
            startTime = "2026-04-24T16:00:00+02:00",
            endTime = "2026-04-25T08:00:00+02:00",
            lineup = listOf(
                ArtistDto("CRYME")
            ),
            description = "description",
            url = "https://de.ra.co/events/2385678",
            flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vZDQ1MDE5YzQwYWQyNGY0YjU0YmU3YWY3NGQzYzAyYThmNGVjYzU0ZC5qcGc=",
            venueAddress = "Clubstraße 123",
            price = 25.0
        ),
        onClick = {}
    )
}