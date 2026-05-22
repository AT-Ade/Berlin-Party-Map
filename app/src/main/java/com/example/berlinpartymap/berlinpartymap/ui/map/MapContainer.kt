package com.example.berlinpartymap.ui.map

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.berlinpartymap.R
import com.example.berlinpartymap.data.remote.dto.EventDto
import com.example.berlinpartymap.ui.components.CustomMapInfoWindow
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.maplibre.compose.camera.CameraState
import org.maplibre.compose.expressions.dsl.const
import org.maplibre.compose.expressions.dsl.image
import org.maplibre.compose.layers.SymbolLayer
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.sources.GeoJsonData
import org.maplibre.compose.sources.rememberGeoJsonSource
import org.maplibre.compose.style.BaseStyle
import org.maplibre.compose.util.ClickResult
import org.maplibre.spatialk.geojson.*

@Composable
fun MapContainer(
    cameraState: CameraState,
    highlightedEvent: EventDto?,
    onCloseInfo: () -> Unit,
    mapHeight: Dp,
    elevation: Dp,
    mapListToggle: Boolean,
    onToggle: () -> Unit,
    locations: List<Feature<Point, JsonObject>>,
    onMarkerClick: (String) -> Unit   // gibt die Event-ID (url) zurück
) {
    // FIX ANR #1 – Stabile GeoJSON-Source:
    // Vorher wurde bei jeder Recomposition `FeatureCollection(locations)` als neues
    // Objekt übergeben. Da `animateDpAsState` für mapHeight/listHeight bei jedem
    // Animations-Frame eine Recomposition auslöst (bis zu 60×/Sek.), hat MapLibre
    // die Karte bei jedem Frame komplett neu geladen → Endlosschleife + ANR.
    // `remember(locations)` cacht das Objekt und erstellt es nur neu, wenn sich
    // die tatsächlichen Event-Daten ändern.
    val featureCollection = remember(locations) {
        FeatureCollection(locations)
    }

    // FIX ANR #2 – Gecachtes Marker-Icon:
    // `painterResource()` wurde vorher innerhalb des MapLibre-Scopes bei jeder
    // Recomposition aufgerufen, was wiederholtes Dekodieren der Ressource verursacht hat.
    // Außerhalb platziert wird der Painter einmal erstellt und danach wiederverwendet.
    val markerPainter = painterResource(R.drawable.markerwhite)

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
                baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/dark")
            ) {
                // Stabiles Objekt übergeben – MapLibre lädt die Source nur neu
                // wenn sich der Inhalt tatsächlich geändert hat.
                val source = rememberGeoJsonSource(
                    data = GeoJsonData.Features(featureCollection)
                )

                SymbolLayer(
                    id = "event-markers",
                    source = source,
                    iconImage = image(markerPainter),
                    iconSize = const(1f),
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

        // Info-Popup wenn ein Event hervorgehoben ist
        if (highlightedEvent != null) {
            CustomMapInfoWindow(
                event = highlightedEvent,
                onClose = onCloseInfo,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 48.dp)
                    .offset(y = -105.dp)
            )
        }

        // Overlay-Karte verhindert Interaktion wenn die Liste vorne ist
        if (!mapListToggle) {
            Card(
                modifier = Modifier.matchParentSize(),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                onClick = onToggle
            ) {}
        }
    }
}
