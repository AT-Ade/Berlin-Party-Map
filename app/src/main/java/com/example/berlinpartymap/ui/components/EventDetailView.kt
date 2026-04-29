package com.example.berlinpartymap.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Euro
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.berlinpartymap.R
import com.example.berlinpartymap.data.remote.dto.EventDto
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun EventDetailView(
    modifier: Modifier = Modifier,
    event: EventDto,
    onClick: () -> Unit
) {
    //Parsen des ISO-Strings
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


    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onClick,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("< zurück")
                }
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                        .size(180.dp),
                    //.clip(CircleShape),
                    contentScale = ContentScale.FillHeight
                )
            }
        }
        item{
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // --------- Titel ---------

                Text(
                    event.name,
                    color = Color.LightGray,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
                //Row(modifier = Modifier.fillMaxWidth(),
                  //  horizontalArrangement = Arrangement.SpaceEvenly) {

                // --------- Datum ---------

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CalendarToday,
                            contentDescription = "Calendar Icon",
                            tint = Color.White,
                            modifier = Modifier.size(25.dp)
                        )
                        Text(
                            startDateString,
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }

                // --------- Uhrzeit ---------

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AccessTime,
                            contentDescription = "Time Icon",
                            tint = Color.White,
                            modifier = Modifier.size(25.dp)
                        )
                        Text(
                            startTimeString + " - " + endTimeString,
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }

                // --------- Location ---------

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "Location Icon",
                        tint = Color.White,
                        modifier = Modifier.size(25.dp)
                    )
                    Text(
                        event.venueName,
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    //HIER MUSS NOCH EINE RICHTIGE ADRESSE HIN
                }

                // -------- PREISE --------

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Euro,
                        contentDescription = "Time Icon",
                        tint = Color.White,
                        modifier = Modifier.size(25.dp)
                    )
                    Text(
                        "???",
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }

               // }
            }
        }

                // --------- LINEUP ---------

        item{
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    "LINEUP",
                    color = Color.LightGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    fontStyle = FontStyle.Italic
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp), // Abstand zwischen den Cards horizontal
                    verticalArrangement = Arrangement.spacedBy(4.dp)    // Abstand zwischen den Zeilen
                ) {
                    event.lineup.forEach { artist ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .padding(4.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(10.dp)
                                )
                        ) {
                            Text(artist, modifier = Modifier.padding(8.dp))
                        }
                    }
                }
            }
        }

            // ------------- Beschreibung -------------

        item {

            Column(modifier = Modifier.fillMaxWidth().padding(8.dp)){
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EventDetailViewPreview() {
    // Use Theme here
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(600.dp)
                .shadow(
                    shape = RoundedCornerShape(10.dp),
                    elevation = 20.dp,
                    spotColor = Color.White,
                    ambientColor = Color.White
                ),
            border = BorderStroke(width = 1.dp, color = Color.White),
            colors = CardDefaults.cardColors(Color.Black.copy(0.97f))
        ) {
            EventDetailView(
                event = EventDto(
                    name = "WHALIEN pres. AREA ONE B2B Niotech (ALL NIGHT LONG) invites Doruksen, 3LEEZA + friends",
                    venueName = "Club Berlin",
                    latitude = 52.4837,
                    longitude = 13.4482,
                    startTime = "2026-04-24T16:00:00+02:00",
                    endTime = "2026-04-25T08:00:00+02:00",
                    lineup = listOf(
                        "CRYME", "ESVEAE", "Floorplan (Robert Hood & Lyric Hood)",
                        "Handmade b2b Stella Zekri", "Natalie Robinson", "Power Squad"
                    ),
                    description = "description",
                    url = "https://de.ra.co/events/2385678",
                    flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vZDQ1MDE5YzQwYWQyNGY0YjU0YmU3YWY3NGQzYzAyYThmNGVjYzU0ZC5qcGc="
                ),
                onClick = {}
            )
        }
    }
}