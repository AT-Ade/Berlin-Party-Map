package com.example.berlinpartymap.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.berlinpartymap.R
import com.example.berlinpartymap.data.remote.dto.ArtistDto
import com.example.berlinpartymap.data.remote.dto.EventDto

@Composable
fun CustomMapInfoWindow(
    event: EventDto,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(0.7f),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(R.drawable.infoboxwhite),
            contentDescription = "Infobox",
            modifier = Modifier.fillMaxWidth()
        )

        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            shape = RoundedCornerShape(15.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = event.venueName,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
                Text(
                    text = event.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
//                Text(
//                    text = "Tap für Details",
//                    style = MaterialTheme.typography.bodySmall,
//                    color = Color.LightGray
//                )
                Button(
                    onClick = onClose,
                    modifier = Modifier.align(Alignment.End).padding(top = 8.dp)
                ) {
                    Text("Schließen")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomInfoBoxPreview() {
    // Use Theme here
    CustomMapInfoWindow(
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
        onClose = {}
    )
}
