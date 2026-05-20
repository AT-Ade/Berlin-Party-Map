package com.example.berlinpartymap.ui.savedevents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.berlinpartymap.data.remote.dto.ArtistDto
import com.example.berlinpartymap.data.remote.dto.EventDto
import com.example.berlinpartymap.ui.components.EventListItem
import org.koin.androidx.compose.koinViewModel

//import org.koin.androidx.compose.koinViewModel


@Composable
fun SavedEventsScreen(
    modifier: Modifier = Modifier,
    viewModel: SavedEventsViewModel = koinViewModel()
) {
    // Hier lauscht die UI auf Änderungen in der Datenbank
    val savedEventsWithLineup by viewModel.savedEvents.collectAsState()



    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Meine gespeicherten Events",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 16.dp)
            )

        if (savedEventsWithLineup.isEmpty()) {
            Text(
                text = "Noch keine Events gespeichert.",
                modifier = Modifier.padding(top = 16.dp),
                fontStyle = FontStyle.Italic
                )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // savedEventsWithLineup enthält die Relationen
                items(savedEventsWithLineup) { item ->
                    val eventEntity = item.event
                    //val artistsList = item.artists
//                    EventListItem(
//                        event = EventDto(
//                            name = item.event.name,
//                            venueName = item.event.venueName,
//                            venueAddress = item.event.venueAddress,
//                            description = item.event.description,
//                            startTime = item.event.startTime,
//                            endTime = item.event.endTime,
//                            latitude = item.event.latitude,
//                            price = item.event.price,
//                            lineup = listOf<ArtistDto>(
//                                item.lineup.
//                            )
//                            ),
//                        modifier = TODO(),
//                        onClick = TODO()
//                    )

                    SavedEventListItem(event = item.event, onClick = {})

//                    Card(modifier = Modifier.fillMaxWidth()) {
//                        Column(modifier = Modifier.padding(16.dp)) {
//                            Text(text = eventEntity.name, fontWeight = FontWeight.Bold)
//                            Text(text = eventEntity.venueName)
//                            Text(eventEntity.startTime)
//
//                            // Zeigt die Künstler an, falls vorhanden
////                            if (artistsList.isNotEmpty()) {
////                                Text(
////                                    text = "Lineup: ${artistsList.joinToString { it.name }}",
////                                    style = MaterialTheme.typography.bodySmall
////                                )
////                            }
//                        }
//                    }
                }
            }
        }
    }
}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SavedEventsScreenPreview() {
    SavedEventsScreen()
}