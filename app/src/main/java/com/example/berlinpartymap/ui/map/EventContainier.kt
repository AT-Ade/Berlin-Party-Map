package com.example.berlinpartymap.ui.map

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    mapListToggle: Boolean,
    onToggle: () -> Unit,
    onEventClick: (EventDto) -> Unit,
    onBack: () -> Unit,
    saveButtonclick: (EventDto) -> Unit,
    isSaved: Boolean
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
                targetState = eventSelected,
                transitionSpec = {
                    if (targetState) {
                        slideInHorizontally { it } + fadeIn() togetherWith
                                slideOutHorizontally { -it } + fadeOut()
                    } else {
                        slideInHorizontally { -it } + fadeIn() togetherWith
                                slideOutHorizontally { it } + fadeOut()
                    }.using(SizeTransform(false))
                }
            ) { selected ->

                if (!selected) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(16.dp)
                            .clip(RoundedCornerShape(15.dp)),
                        verticalArrangement = Arrangement.spacedBy(8.dp),

                    ) {
                        items(events) {
                            EventListItem(event = it, onClick = {
                                onEventClick(it)
                            })
                        }
                    }
                } else {
                    selectedEvent?.let {
                        EventDetailView(event = it,
                            onClick = onBack,
                            saveButtonClick = { saveButtonclick },
                            isSaved = isSaved
                        )
                    }
                }
            }
        }

        if (mapListToggle) {
            Card(
                modifier = Modifier.matchParentSize(),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                onClick = onToggle
            ) {}
        }
    }
}

