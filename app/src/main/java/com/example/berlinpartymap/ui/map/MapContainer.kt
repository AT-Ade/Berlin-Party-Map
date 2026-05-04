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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.tileprovider.tilesource.ITileSource
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView


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
    mapHeight: Dp,
    elevation: Dp,
    mapListToggle: Boolean,
    onToggle: () -> Unit
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
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { mapView },
                update = {


                    it.setTileSource(getDarkTileSource())
                    it.setMultiTouchControls(true)
                    it.controller.setZoom(15.0)
                    it.controller.setCenter(GeoPoint(52.52, 13.405))
                }
            )
        }

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

