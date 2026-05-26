package com.example.berlinpartymap.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Euro
import androidx.compose.material.icons.outlined.Link // IMPORTIERT FÜR DAS LINK-ICON
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler // IMPORTIERT FÜR DEN BROWSER-AUFRUF
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration // IMPORTIERT FÜR DIE UNTERSTREICHUNG
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
fun EventDetailView(
    modifier: Modifier = Modifier,
    event: EventDto,
    onClick: () -> Unit,
    saveButtonClick: (EventDto) -> Unit,
    isSaved: Boolean,
    likedArtistNames: Set<String>
) {
    // UriHandler holen, um Links im Browser zu öffnen
    val uriHandler = LocalUriHandler.current

    //Parsen des ISO-Strings
    val zonedDateStartTime = ZonedDateTime.parse(event.startTime)
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY)
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.GERMANY)

    val startDateString = zonedDateStartTime.format(dateFormatter)
    val startTimeString = zonedDateStartTime.format(timeFormatter)

    val zonedDateEndTime = ZonedDateTime.parse(event.endTime)
    val endDateString = zonedDateEndTime.format(dateFormatter)
    val endTimeString = zonedDateEndTime.format(timeFormatter)

    Column(){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StyledIconButton(
                onClick = onClick,
                icon = Icons.Rounded.ArrowBackIosNew,
                iconColor = Color.White
            )

            StyledIconButton(
                onClick = { saveButtonClick(event) },
                icon = if (isSaved) Icons.Rounded.Star else Icons.TwoTone.Star,
                iconColor = if (isSaved) Color.Green else Color.White
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
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
            item {
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
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = "Location Icon",
                            tint = Color.White,
                            modifier = Modifier.size(25.dp)
                        )
                        Column() {
                            Text(
                                event.venueName,
                                color = Color.White,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(horizontal = 8.dp),
                                fontWeight = FontWeight.Medium
                            )

                            Text(
                                event.venueAddress,
                                color = Color.White,
                                fontSize = 17.sp,
                                modifier = Modifier.padding(horizontal = 8.dp),
                                fontStyle = FontStyle.Italic
                            )
                        }
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
                            contentDescription = "Price Icon",
                            tint = Color.White,
                            modifier = Modifier.size(25.dp)
                        )
                        Text(
                            if (event.price != 0.0) "${event.price.roundToInt()}€" else "free",
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }

                    // -------- LINK / MEHR INFOS --------
                    // Wird nur angezeigt, wenn auch wirklich eine URL im Model hinterlegt ist
                    if (!event.url.isNull_or_Blank()) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .clickable {
                                    // Öffnet den Link sicher im Browser
                                    uriHandler.openUri(event.url.trim())
                                },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Link,
                                contentDescription = "Link Icon",
                                tint = Color(0xFF64B5F6), // Schönes, dezentes Blau für Verlinkungen
                                modifier = Modifier.size(25.dp)
                            )
                            Text(
                                text = "mehr Infos",
                                color = Color(0xFF64B5F6),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.Underline, // Macht den Link visuell erkennbar
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    }
                }
            }

            // --------- LINEUP ---------

            item {
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
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        event.lineup.forEach { artist ->
                            val isLiked = likedArtistNames.contains(artist.name)

                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.Transparent,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .border(
                                        width = 1.dp,
                                        color = if (isLiked) Color.Red else Color.White,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(artist.name, modifier = Modifier.padding(0.dp))
                                    if (isLiked) {
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

            // ------------- Beschreibung -------------

            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {
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

// Extension-Hilfsfunktion für die Sicherheit (behebt Tippfehler im Prompt-Kontext)
private fun String?.isNull_or_Blank(): Boolean = this == null || this.trim().isEmpty()

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EventDetailViewPreview() {
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
                        ArtistDto("CRYME")
                    ),
                    description = "description",
                    url = "https://de.ra.co/events/2385678",
                    flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vZDQ1MDE5YzQwYWQyNGY0YjU0YmU3YWY3NGQzYzAyYThmNGVjYzU0ZC5qcGc=",
                    venueAddress = "Clubstraße 123",
                    price = 25.0
                ),
                onClick = {},
                saveButtonClick = {},
                isSaved = true,
                likedArtistNames = emptySet()
            )
        }
    }
}