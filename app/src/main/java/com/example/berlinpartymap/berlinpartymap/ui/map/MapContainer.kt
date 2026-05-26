package com.example.berlinpartymap.ui.map

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.berlinpartymap.data.remote.dto.EventDto
import com.example.berlinpartymap.ui.components.CustomMapInfoWindow
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.maplibre.compose.camera.CameraState
import org.maplibre.compose.expressions.dsl.Feature.get
import org.maplibre.compose.expressions.dsl.asString
import org.maplibre.compose.expressions.dsl.case
import org.maplibre.compose.expressions.dsl.const
import org.maplibre.compose.expressions.dsl.image
import org.maplibre.compose.expressions.dsl.switch
import org.maplibre.compose.layers.SymbolLayer
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.sources.GeoJsonData
import org.maplibre.compose.sources.rememberGeoJsonSource
import org.maplibre.compose.style.BaseStyle
import org.maplibre.compose.util.ClickResult
import com.example.berlinpartymap.R
import org.maplibre.spatialk.geojson.*

@Composable
fun MapContainer(
    cameraState: CameraState,
    highlightedEvent: EventDto?, // Steuert nur noch das Info-Window oben
    selectedEvent: EventDto?,    // NEU: Steuert jetzt die Pin-Farbe und Animation!
    onCloseInfo: () -> Unit,
    mapHeight: Dp,
    elevation: Dp,
    mapListToggle: Boolean,
    onToggle: () -> Unit,
    locations: List<Feature<Point, JsonObject>>,
    onMarkerClick: (String) -> Unit,
    isSaved: Boolean,                    // <-- NEU HIER
    likedArtistNames: Set<String>,
    onDetailClick: () -> Unit
) {
    val featureCollection = remember(locations) {
        FeatureCollection(locations)
    }

    val whiteMarkerPainter = painterResource(R.drawable.markerwhite)
    val goldMarkerPainter = painterResource(R.drawable.markergold)

    val whiteIcon = image(whiteMarkerPainter)
    val goldIcon = image(goldMarkerPainter)

    // Die Pin-Animation reagiert jetzt vollkommen autark auf das 'selectedEvent'!
    var animatedEventId by remember { mutableStateOf("") }
    val animatedScale = remember { Animatable(1.0f) }

    LaunchedEffect(selectedEvent) {
        if (selectedEvent != null) {
            animatedEventId = selectedEvent.url
            animatedScale.animateTo(1.4f, animationSpec = tween(250))
        } else {
            animatedScale.animateTo(1.0f, animationSpec = tween(250))
            animatedEventId = ""
        }
    }

    Box {
        Card(
            modifier = Modifier
                .padding(top = 32.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                .fillMaxWidth()
                .height(mapHeight)
                .shadow(elevation, RoundedCornerShape(10.dp)),
            border = BorderStroke(1.dp, Color.White)
        ) {
            MaplibreMap(
                modifier = Modifier.fillMaxSize(),
                cameraState = cameraState,
                baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/dark"),
                onMapClick = { _, _ ->
                    onCloseInfo() // Schließt bei Klick ins Leere NUR die Infobox (Selection bleibt!)
                    ClickResult.Pass
                }
            ) {
                val source = rememberGeoJsonSource(
                    data = GeoJsonData.Features(featureCollection)
                )

                SymbolLayer(
                    id = "event-markers",
                    source = source,

                    iconImage = switch(
                        get("id").asString(),
                        case(animatedEventId, goldIcon),
                        fallback = whiteIcon
                    ),

                    iconSize = switch(
                        get("id").asString(),
                        case(animatedEventId, const(animatedScale.value)),
                        fallback = const(1.0f)
                    ),

                    iconAllowOverlap = const(true),
                    iconIgnorePlacement = const(true),
                    onClick = { clickedFeatures: List<Feature<Geometry, JsonObject?>> ->
                        val idValue = clickedFeatures
                            .firstOrNull()
                            ?.properties
                            ?.get("id")
                            ?.jsonPrimitive
                            ?.content

                        if (idValue != null) {
                            onMarkerClick(idValue)
                            ClickResult.Consume
                        } else {
                            ClickResult.Pass
                        }
                    }
                )
            }
        }

        // Das Info-Window reagiert weiterhin brav auf den highlighted-Zustand
        if (highlightedEvent != null) {
            CustomMapInfoWindow(
                event = highlightedEvent,
                onClose = onCloseInfo,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 48.dp)
                    .offset(y = -120.dp),
                isSaved = isSaved,
                likedArtistNames = likedArtistNames,
                onClick = onDetailClick
            )
        }

        if (!mapListToggle) {
            Card(
                modifier = Modifier.matchParentSize(),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                onClick = onToggle
            ) {}
        }
    }
}