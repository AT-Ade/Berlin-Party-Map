package com.example.berlinpartymap.ui.map

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.KeyboardDoubleArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.berlinpartymap.berlinpartymap.ui.components.EventInfoBlock
import com.example.berlinpartymap.data.remote.dto.EventDto
import com.example.berlinpartymap.ui.components.EventDetailView
import com.example.berlinpartymap.ui.components.EventListItem
import com.example.berlinpartymap.ui.components.StyledIconButton

@Composable
fun EventContainer(
    modifier: Modifier = Modifier,
    events: List<EventDto>,
    uiState: EventUiState,
    formattedDate: String,
    onPrevDate: () -> Unit,
    onNextDate: () -> Unit,
    onDatePickerClick: () -> Unit,
    onRetry: () -> Unit,
    selectedEvent: EventDto?,
    eventSelected: Boolean,
    //listHeight: Dp,
    elevation: Dp,
    mapListToggle: Boolean,
    onToggle: () -> Unit,
    onEventClick: (EventDto) -> Unit,
    onBack: () -> Unit,
    saveButtonclick: (EventDto) -> Unit,
    isSaved: Boolean,
    likedArtistNames: Set<String>
) {
    Box(modifier = modifier) {

        Column(
            modifier = Modifier
                .padding(top = 4.dp, start = 8.dp, end = 8.dp)
                .fillMaxSize() // Auch hier fillMaxSize statt height!
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
                        fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(150))
                    } else {
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
                    // Eine umschließende Column sorgt dafür, dass die Buttons oben stehen und die Liste darunter abfängt
                    Column(modifier = Modifier.fillMaxSize()) {

                        // Die Button Row ist nun HIER im animierten Bereich integriert
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Tag zurück
                            StyledIconButton(
                                onClick = onPrevDate,
                                icon = Icons.AutoMirrored.Rounded.ArrowBackIos,
                                iconColor = Color.White
                            )

                            // Hauptbutton für DatePicker
                            OutlinedButton(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(40.dp),
                                onClick = onDatePickerClick,
                                shape = CircleShape,
                                border = BorderStroke(1.dp, Color.White),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = Color.White.copy(alpha = 0.15f)
                                )
                            ) {
                                Text(
                                    text = formattedDate,
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            // Tag vorwärts
                            StyledIconButton(
                                onClick = onNextDate,
                                icon = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                                iconColor = Color.White
                            )
                        }

                        // Der eigentliche Content-Bereich (Loading, Error, List) füllt den restlichen Platz
                        Box(
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            when (uiState) {
                                is EventUiState.Loading -> {
                                    CircularProgressIndicator(color = Color.White)
                                }

                                is EventUiState.Error -> {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "Verbindung fehlgeschlagen",
                                            color = MaterialTheme.colorScheme.error,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Button(onClick = onRetry) {
                                            Text("Erneut versuchen")
                                        }
                                    }
                                }

                                is EventUiState.Success -> {
                                    if (events.isEmpty()) {
                                        Text(
                                            text = "Für diesen Tag wurden leider keine Events gefunden. 🕺💤",
                                            color = Color.White.copy(alpha = 0.6f),
                                            textAlign = TextAlign.Center,
                                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                            modifier = Modifier.padding(16.dp)
                                        )
                                    } else {
                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp)
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
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // 2. FALL: Ein Event wurde ausgewählt! (Hier fliegt die Button Row automatisch mit raus)
                    selectedEvent?.let { event ->
                        if (isContainerSmall) {
                            EventInfoBlock(event = event)
                        } else {
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

        // JETZT MIT ANIMATION: Ersetzt das nackte "if (mapListToggle)"
        AnimatedVisibility(
            visible = mapListToggle,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Card(
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .matchParentSize()
                    .padding(top = 65.dp, start = 8.dp, end = 8.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = if (selectedEvent == null) listOf(Color.Transparent, Color.Black)
                            else listOf(Color.Transparent, Color.Transparent)
                        ),
                        shape = RoundedCornerShape(15.dp)
                    ),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                onClick = onToggle,
            ) {
                if (selectedEvent == null) {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.KeyboardDoubleArrowUp,
                                contentDescription = "Upwards arrows",
                                tint = Color.White
                            )
                            Text(
                                "Liste anzeigen",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}