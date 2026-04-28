package com.example.berlinpartymap.ui.map

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.berlinpartymap.R
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.berlinpartymap.ui.components.EventListItem
import kotlinx.coroutines.flow.forEach

//import org.koin.androidx.compose.koinViewModel


@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    mapViewModel: MapViewModel = viewModel() // ViewModel für Daten (z.B. Events)
) {

    val events by mapViewModel.events.collectAsState()
    val clubs by mapViewModel.clubs.collectAsState()

    var mapListToggle by remember { mutableStateOf(true) }
    //val mapHeight = if (mapListToggle) 600.dp else 200.dp
    //val listHeight = if (mapListToggle) 200.dp else 600.dp

//    val mapShadowElevation = if (mapListToggle) 20.dp else 15.dp
//    val listShadowElevation = if (mapListToggle) 15.dp else 20.dp

    val mapHeight by animateDpAsState(
        targetValue = if (mapListToggle) 600.dp else 200.dp,
        animationSpec = tween(durationMillis = 200), // Dauer der Animation
        label = "mapHeightAnim"
    )

    val listHeight by animateDpAsState(
        targetValue = if (mapListToggle) 200.dp else 600.dp,
        animationSpec = tween(durationMillis = 200),
        label = "listHeightAnim"
    )

    val animatedMapElevation by animateDpAsState(
        targetValue = if (mapListToggle) 20.dp else 5.dp,
        label = "mapElevationAnim"
    )

    val animatedListElevation by animateDpAsState(
        targetValue = if (!mapListToggle) 20.dp else 5.dp,
        label = "mapElevationAnim"
    )



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

    // Wird einmal beim Start ausgeführt (z.B. Daten laden)
    LaunchedEffect(Unit) {
        mapViewModel.loadEvents()
    }
    LaunchedEffect(Unit) {
        mapViewModel.loadClubs()
    }

    LaunchedEffect(events) {
        mapView.overlays.clear()

        events.forEach { event ->
            val marker = Marker(mapView).apply {
                position = GeoPoint(event.latitude, event.longitude)
                title = event.venueName
            }
            mapView.overlays.add(marker)
        }

        mapView.invalidate()
    }

//    LaunchedEffect(clubs) {
//        mapView.overlays.clear()
//
//        clubs.forEach { club ->
//            val marker = Marker(mapView).apply {
//                position = GeoPoint(club.latitude, club.longitude)
//                title = club.name
//            }
//            mapView.overlays.add(marker)
//        }
//
//        mapView.invalidate()
//    }

    // Bindet klassische Android View (MapView) in Compose ein
    Box(){
            Image(
                painter = painterResource(id = R.drawable.blacklightning),
                contentDescription = null, // Hintergrundbilder brauchen oft keine Beschreibung
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Füllt den Bildschirm aus, schneidet Ränder ggf. ab
            )
        Column(
            verticalArrangement = Arrangement.Top
        ) {

            // --------KARTE--------

            Box(){

                Card(
                    modifier = Modifier
                        .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                        .height(mapHeight)
                        .shadow(
                            shape = RoundedCornerShape(10.dp),
                            elevation = animatedMapElevation,
                            spotColor = Color.White,
                            ambientColor = Color.White
                        ),
                    border = BorderStroke(width = 1.dp, color = Color.White)
                ) {
                    AndroidView(
                        modifier = modifier
                            //.padding(16.dp)
                            .fillMaxSize(),
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
                if (!mapListToggle) {
                    Card(
                        modifier = Modifier
                            .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                            .height(mapHeight),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(0f)),
                        onClick = { mapListToggle = !mapListToggle }
                    ) {

                    }
                }

            }

            // -------- LISTE ----------

            Box(modifier = Modifier.height(listHeight)) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(listHeight)
                        .shadow(
                            shape = RoundedCornerShape(10.dp),
                            elevation = animatedListElevation,
                            spotColor = Color.White,
                            ambientColor = Color.White
                        ),
                    border = BorderStroke(width = 1.dp, color = Color.White),
                    colors = CardDefaults.cardColors(Color.Black.copy(0.97f))
                ) {
                    LazyColumn {
                        items(events) { event ->
                            EventListItem(event = event)
                        }
                    }
                }
                if (mapListToggle) {
                    Card(
                        modifier = Modifier
                            .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                            .height(listHeight),
                        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0f)),
                        onClick = { mapListToggle = !mapListToggle }
                    ) {

                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MapScreenPreview() {
    MapScreen()
}