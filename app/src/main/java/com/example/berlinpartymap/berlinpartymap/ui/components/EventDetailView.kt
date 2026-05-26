package com.example.berlinpartymap.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Euro
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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
    val uriHandler = LocalUriHandler.current

    // -------- ANIMATIONS LOGIK FOR THE FAVORITE BUTTON --------
    // Bestimmt die Ziel-Rotation: 360 Grad wenn favorisiert, 0 Grad wenn un-favorisiert
    val rotationTarget = if (isSaved) 360f else 0f
    val rotation by animateFloatAsState(
        targetValue = rotationTarget,
        // 350ms ist die perfekte "Snappy"-Länge für eine spürbare, aber kurze Drehung
        animationSpec = tween(durationMillis = 350)
    )

    // Flüssiger Farbwechsel zum Club-Gelb (0xFFFFD700) oder zurück zu Weiß
    val animatedStarColor by animateColorAsState(
        targetValue = if (isSaved) Color(0xFFFFD700) else Color.White,
        animationSpec = tween(durationMillis = 250)
    )

    //Parsen des ISO-Strings
    val zonedDateStartTime = ZonedDateTime.parse(event.startTime)
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY)
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.GERMANY)

    val startDateString = zonedDateStartTime.format(dateFormatter)
    val startTimeString = zonedDateStartTime.format(timeFormatter)

    val zonedDateEndTime = ZonedDateTime.parse(event.endTime)
    val endDateString = zonedDateEndTime.format(dateFormatter)
    val endTimeString = zonedDateEndTime.format(timeFormatter)

    Column(modifier = modifier){
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

            // Hier übergeben wir den Rotations-Modifier direkt an das Icon via StyledIconButton
            StyledIconButton(
                onClick = { saveButtonClick(event) },
                icon = if (isSaved) Icons.Rounded.Star else Icons.TwoTone.Star,
                iconColor = animatedStarColor, // Nutzen der animierten Farbe
                modifier = Modifier.rotate(rotation) // Nutzen des Drehungswinkels
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
                            .padding(bottom = 16.dp)
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
                    if (!event.url.isNull_or_Blank()) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .clickable {
                                    uriHandler.openUri(event.url.trim())
                                },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Link,
                                contentDescription = "Link Icon",
                                tint = Color(0xFF64B5F6),
                                modifier = Modifier.size(25.dp)
                            )
                            Text(
                                text = "mehr Infos & Tickets",
                                color = Color(0xFF64B5F6),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.Underline,
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

private fun String?.isNull_or_Blank(): Boolean = this == null || this.trim().isEmpty()