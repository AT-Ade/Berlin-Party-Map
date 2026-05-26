package com.example.berlinpartymap.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Euro
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardDoubleArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star // Importiert für den leeren Stern
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.berlinpartymap.R
import com.example.berlinpartymap.data.remote.dto.ArtistDto
import com.example.berlinpartymap.data.remote.dto.EventDto
import kotlin.math.roundToInt

@Composable
fun CustomMapInfoWindow(
    event: EventDto,
    onClose: () -> Unit,
    isSaved: Boolean,
    likedArtistNames: Set<String>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    // Anzahl der gelikten Artists für dieses Event berechnen
    val likedArtistsCount = remember(event.lineup, likedArtistNames) {
        event.lineup.count { artist -> likedArtistNames.contains(artist.name) }
    }

    Box(
        modifier = modifier.fillMaxWidth(0.75f),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.infoboxwhite),
            contentDescription = "Infobox",
            modifier = Modifier.fillMaxWidth()
        )

        Card(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 24.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            shape = RoundedCornerShape(15.dp)
        ) {
            Column(
                modifier = Modifier.height(180.dp).padding(8.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // Erste Zeile: Location-Name & Favoriten-Stern (gefüllt/gold oder leer/grau)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = event.venueName,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        imageVector = if (isSaved) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = if (isSaved) "Favorisiert" else "Nicht favorisiert",
                        tint = if (isSaved) Color(0xFFFFB300) else Color.LightGray,
                        modifier = Modifier.size(25.dp)
                    )
                }

                // Zweite Zeile: Event-Name
                Text(
                    text = event.name,
                    fontSize = 13.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    color = Color.DarkGray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // Dritte Zeile: Preis & Badge für gelikte Artists (immer sichtbar, wechselt Farbe)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Preis-Anzeige

                        Text(
                            text = if (event.price != 0.0) "${event.price.roundToInt()}€" else "free",
                            fontStyle = FontStyle.Italic,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (event.price == 0.0) Color(0xFF2E7D32) else Color.Black
                        )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.border(
                            width = 1.dp,
                            shape = CircleShape,
                            color = Color.Black
                        )
                            .clickable(onClick = onClick)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardDoubleArrowDown,
                            contentDescription = "Gelikter Artist Status",
                            tint = Color.Black,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    // Dynamische Farbzuweisung für das Artist-Badge
                    val badgeColor = if (likedArtistsCount > 0) Color.Red else Color.LightGray

                    Row(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = badgeColor,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = likedArtistsCount.toString(),
                            color = badgeColor,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Gelikter Artist Status",
                            tint = badgeColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomInfoBoxPreview() {
    CustomMapInfoWindow(
        event = EventDto(
            name = "WHALIEN pres. AREA ONE B2B Niotech (ALL NIGHT LONG) invites Doruksen, 3LEEZA + friends",
            venueName = "Club Berlin",
            latitude = 52.4837,
            longitude = 13.4482,
            startTime = "2026-04-24T16:00:00+02:00",
            endTime = "2026-04-25T08:00:00+02:00",
            lineup = listOf(
                ArtistDto("CRYME"),
                ArtistDto("Doruksen")
            ),
            description = "description",
            url = "https://de.ra.co/events/2385678",
            flyerURL = "",
            venueAddress = "Clubstraße 123",
            price = 25.0
        ),
        onClose = {},
        onClick = {},
        isSaved = false, // Preview zeigt jetzt den ungesicherten, grauen Zustand
        likedArtistNames = emptySet() // Preview zeigt jetzt 0 gelikte Artists (Graues Badge)
    )
}