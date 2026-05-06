package com.example.berlinpartymap.ui.map

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.berlinpartymap.R
import com.example.berlinpartymap.data.remote.dto.EventDto
import com.example.berlinpartymap.ui.components.CustomMapInfoWindow
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.expressions.dsl.const
import org.maplibre.compose.expressions.dsl.image
import org.maplibre.compose.layers.SymbolLayer
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.sources.GeoJsonData
import org.maplibre.compose.sources.Source
import org.maplibre.compose.sources.rememberGeoJsonSource
import org.maplibre.compose.style.BaseStyle
import org.maplibre.spatialk.geojson.Point
import org.osmdroid.tileprovider.tilesource.ITileSource
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.maplibre.spatialk.geojson.Position
import org.maplibre.spatialk.geojson.*

fun getLightTileSource(): ITileSource {
    return XYTileSource(
        "CartoLight",
        0, 20, 256, ".png",
        arrayOf(
            "https://a.basemaps.cartocdn.com/light_all/",
            "https://b.basemaps.cartocdn.com/light_all/",
            "https://c.basemaps.cartocdn.com/light_all/"
        )
    )

}


fun getDarkTileSource(): ITileSource {
    return XYTileSource(
        "CartoDark",
        0, 20, 256, ".png",
        arrayOf(
            "https://a.basemaps.cartocdn.com/dark_all/",
            "https://b.basemaps.cartocdn.com/dark_all/",
            "https://c.basemaps.cartocdn.com/dark_all/"
        )
    )
}
@Composable
fun MapContainer(
    mapView: MapView,
    highlightedEvent: EventDto?, // Neu: Den State übergeben
    onCloseInfo: () -> Unit,  // Neu: Callback zum Schließen
    mapHeight: Dp,
    elevation: Dp,
    mapListToggle: Boolean,
    onToggle: () -> Unit,
    eventPins: List<GeoJsonData.Features>
    //onEventClick: (EventDto) -> Unit
) {
    Box {
        Card(
            modifier = Modifier
                .padding(top = 32.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                .fillMaxWidth()
                .height(mapHeight)
                .shadow(elevation, RoundedCornerShape(10.dp)),
            border = BorderStroke(1.dp, Color.White)
        ) {
            // -------- ALTE OPENSTREETMAP ----------
//            AndroidView(
//                modifier = Modifier.fillMaxSize(),
//                factory = { mapView },
//                update = {
//                    it.setTileSource(getLightTileSource())
//                    it.setMultiTouchControls(true)
//                    it.controller.setZoom(15.0)
//                    it.controller.setCenter(GeoPoint(52.52, 13.405))
//                }
//            )

            // -_-_-_-_-_-_- NEUE MAPLIBRE -_-_-_-_-_-_-

            val cameraState = rememberCameraState(
                CameraPosition(
                    target = Position(13.4050, 52.5200),
                    zoom = 10.0
                )
            )

            MaplibreMap(
                cameraState = cameraState,
                baseStyle = BaseStyle
                    .Uri("https://tiles.openfreemap.org/styles/dark")
            ) {
                val source = rememberGeoJsonSource(
                    // Ab hier können die Daten aus dem VM stammen z.B.
                    data = GeoJsonData.Features(
                        Point(
                            Position(
                                13.4050,
                                52.5200
                            )
                        )
                    )
                )
                val source2 = rememberGeoJsonSource(
                    // Ab hier können die Daten aus dem VM stammen z.B.
                    data = GeoJsonData.Features(
                        Point(
                            Position(
                                13.4050,
                                52.5200
                            )
                        )
                    )
                )

                SymbolLayer(
                    id = "Marker",
                    source = source,
                    iconImage = image(painterResource(R.drawable.markerwhite)),
                    iconSize = const(2f)
                )
            }

        }
//        if (highlightedEvent != null) {
//            CustomMapInfoWindow(
//                event = highlightedEvent,
//                onClose = onCloseInfo,
//                modifier = Modifier
//                    .align(Alignment.TopCenter) // Position auf der Karte
//                    .padding(top = 16.dp)
//            )
//        }
        if (!mapListToggle) {
            Card(
                modifier = Modifier
                    .matchParentSize(),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                onClick = onToggle
            ) {}
        }
    }
}

