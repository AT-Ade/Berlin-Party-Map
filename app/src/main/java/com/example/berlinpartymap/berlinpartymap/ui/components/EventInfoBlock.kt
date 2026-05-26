package com.example.berlinpartymap.berlinpartymap.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Euro
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.berlinpartymap.R
import com.example.berlinpartymap.data.remote.dto.ArtistDto
import com.example.berlinpartymap.data.remote.dto.EventDto
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun EventInfoBlock(
    event: EventDto,
    modifier: Modifier = Modifier
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

    Row(
        modifier = Modifier.fillMaxSize().padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = event.flyerURL,
            contentDescription = "${event.name} Flyer",
            //placeholder = painterResource(R.drawable.placeholderevent),
            error = painterResource(R.drawable.placeholderevent),
            modifier = Modifier
                .padding(16.dp)
                .size(65.dp),
            //.clip(CircleShape),
            contentScale = ContentScale.FillHeight
        )

        Column(
            Modifier.fillMaxSize().padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
             verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                event.name,
                color = Color.LightGray,
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            //Row(modifier = Modifier.fillMaxWidth(),
            //  horizontalArrangement = Arrangement.SpaceEvenly) {

            // --------- Datum ---------

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarToday,
                        contentDescription = "Calendar Icon",
                        tint = Color.White,
                        modifier = Modifier.size(17.dp)
                    )
                    Text(
                        startDateString,
                        color = Color.White,
                        fontSize = 17.sp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
                //Spacer(modifier = Modifier.width(15.dp))

                // --------- Uhrzeit ---------
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.AccessTime,
                        contentDescription = "Time Icon",
                        tint = Color.White,
                        modifier = Modifier.size(17.dp)
                    )
                    Text(
                        startTimeString + " - " + endTimeString,
                        color = Color.White,
                        fontSize = 17.sp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }


            // --------- Location ---------

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.width(200.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "Location Icon",
                        tint = Color.White,
                        modifier = Modifier.size(17.dp)
                    )
                    Text(
                        event.venueName,
                        color = Color.White,
                        fontSize = 17.sp,
                        modifier = Modifier.padding(horizontal = 4.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // -------- PREISE --------
               //Spacer(modifier = Modifier.width(15.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.width(60.dp)
                ) {
                    if (event.price.roundToInt() == 0) {
                        Icon(
                            imageVector = Icons.Outlined.Euro,
                            contentDescription = "Euro Icon",
                            tint = Color.White,
                            modifier = Modifier.size(17.dp),
                        )
                    }
                    Text(
                        if (event.price != 0.0) "${event.price.roundToInt()}€" else "free",
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        fontSize = 17.sp,
                        modifier = Modifier.padding(horizontal = 4.dp),

                    )
                }
            }




        }

    }
}

@Preview(showBackground = true)
@Composable
private fun EventInfoBlockPreview() {
    // Use Theme here
    EventInfoBlock(
        event = EventDto(
            name = "WHALIEN pres. AREA ONE B2B Niotech (ALL NIGHT LONG) invites Doruksen, 3LEEZA + friends",
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
        )
    )
}