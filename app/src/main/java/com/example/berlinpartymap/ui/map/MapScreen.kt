package com.example.berlinpartymap.ui.map

import android.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

//import org.koin.androidx.compose.koinViewModel


@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    mapViewModel: MapViewModel = viewModel() // ViewModel für Daten (z.B. Events)
) {
    val events = mapViewModel.events.value // State aus dem ViewModel lesen

    val context = LocalContext.current // Android Context (wird für MapView benötigt)
    val lifecycle = LocalLifecycleOwner.current.lifecycle // Lifecycle der Activity

    // MapView wird einmal erstellt und über Recomposition hinweg behalten
    val mapView = remember {
        MapView(context)
    }

    // ❗ Marker sollte auch "remember" sein, sonst wird er mehrfach hinzugefügt
    val marker = remember {
        Marker(mapView).apply {
            position = GeoPoint(52.52, 13.405) // Koordinaten (Berlin)
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM) // Marker-Ausrichtung
            title = "Berlin" // Text beim Anklicken
        }
    }

    // Marker zur Karte hinzufügen (nur wenn noch nicht vorhanden)
    if (!mapView.overlays.contains(marker)) {
        mapView.overlays.add(marker)
    }

    mapView.invalidate() // Karte neu zeichnen

    // Lifecycle an MapView weitergeben (wichtig für Performance & Stabilität)
    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> mapView.onResume() // Karte aktiv
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()   // Karte pausieren
                else -> {}
            }
        }

        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer) // Cleanup
        }
    }

    // Bindet klassische Android View (MapView) in Compose ein
    Card(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth()
            .height(600.dp)
            .shadow(shape = RoundedCornerShape(10.dp), elevation = 10.dp, spotColor = Color.Black),
        border = BorderStroke(width = 2.dp, color = Color.Black)
    ) {
        AndroidView(
            modifier = modifier.padding(16.dp).fillMaxSize(),
            factory = { mapView }, // wird einmal erstellt
            update = {
                // wird bei Recomposition ausgeführt
                it.setTileSource(TileSourceFactory.MAPNIK) // OSM Kartenstil
                it.setMultiTouchControls(true) // Zoom + Gesten aktivieren

                it.controller.setZoom(15.0) // Zoom-Level
                it.controller.setCenter(GeoPoint(52.52, 13.405)) // Startposition
            }
        )
    }

    /*
    // Wird einmal beim Start ausgeführt (z.B. Daten laden)
    LaunchedEffect(Unit) {
        mapViewModel.loadEvents()
    }

    // Beispiel UI für Event-Liste
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn {
            items(events) { event ->
                Text(text = event.name + "\n____________________")
            }
        }
    }
    */
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MapScreenPreview() {
    MapScreen()
}