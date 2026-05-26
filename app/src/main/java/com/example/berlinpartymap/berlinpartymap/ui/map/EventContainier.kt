package com.example.berlinpartymap.ui.map

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.berlinpartymap.berlinpartymap.ui.components.EventInfoBlock
import com.example.berlinpartymap.data.remote.dto.EventDto
import com.example.berlinpartymap.ui.components.EventDetailView
import com.example.berlinpartymap.ui.components.EventListItem

@Composable
fun EventContainer(
    events: List<EventDto>,
    selectedEvent: EventDto?,
    eventSelected: Boolean,
    listHeight: Dp,
    elevation: Dp,
    mapListToggle: Boolean, // true = Container ist KLEIN (200.dp), false = Container ist GROSS (600.dp)
    onToggle: () -> Unit,
    onEventClick: (EventDto) -> Unit,
    onBack: () -> Unit,
    saveButtonclick: (EventDto) -> Unit,
    isSaved: Boolean,
    likedArtistNames: Set<String>
) {
    Box(modifier = Modifier.height(listHeight)) {

        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(listHeight)
                .shadow(elevation, RoundedCornerShape(10.dp))
                .background(Color.Black.copy(0.97f), RoundedCornerShape(15.dp))
                .border(1.dp, Color.White, RoundedCornerShape(15.dp))
        ) {

            AnimatedContent(
                targetState = Pair(eventSelected, mapListToggle),
                transitionSpec = {
                    val oldSelected = initialState.first
                    val newSelected = targetState.first
                    val oldMapToggle = initialState.second
                    val newMapToggle = targetState.second

                    if (oldSelected == newSelected && oldMapToggle != newMapToggle) {
                        // Reines, sauberes Überblenden ohne jegliche Bewegungskomponente
                        fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(150))
                    } else {
                        // FALL B: Der klassische Wechsel zwischen Liste und Event-Auswahl
                        if (newSelected) {
                            (slideInHorizontally { it } + fadeIn()) togetherWith
                                    (slideOutHorizontally { -it } + fadeOut())
                        } else {
                            (slideInHorizontally { -it } + fadeIn()) togetherWith
                                    (slideOutHorizontally { it } + fadeOut())
                        }
                    }.using(SizeTransform(false))
                }
            ) { (selected, isContainerSmall) ->

                if (!selected) {
                    // 1. FALL: Kein Event ausgewählt -> Scroll-Liste
                    LazyColumn(
                        modifier = Modifier
                            .padding(16.dp)
                            .clip(RoundedCornerShape(15.dp)),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(events) { eventDto ->
                            val countOfLikedArtists = eventDto.lineup.count { artistDto ->
                                likedArtistNames.contains(artistDto.name)
                            }

                            EventListItem(
                                event = eventDto,
                                likedArtistsCount = countOfLikedArtists,
                                onClick = { onEventClick(eventDto) }
                            )
                        }
                    }
                } else {
                    // Ein Event wurde ausgewählt!
                    selectedEvent?.let { event ->
                        if (isContainerSmall) {
                            // 2. FALL: Event ausgewählt UND Container ist KLEIN (200.dp)
                            EventInfoBlock(
                                event = event
                            )
                        } else {
                            // 3. FALL: Event ausgewählt UND Container ist GROSS (600.dp)
                            EventDetailView(
                                event = event,
                                onClick = onBack,
                                saveButtonClick = { saveButtonclick(event) },
                                isSaved = isSaved,
                                likedArtistNames = likedArtistNames
                            )
                        }
                    }
                }
            }
        }

        // Hinweis: Ich habe die obere der beiden transparenten Cards entfernt,
        // da deine untere Card ohnehin aktiv ist, sobald mapListToggle == true.
        if (mapListToggle) {
            Card(
                modifier = Modifier.matchParentSize(),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                onClick = onToggle // Bringt den Container sofort von 200.dp auf 600.dp
            ) {}
        }
    }
}