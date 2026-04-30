package com.example.berlinpartymap.ui.map

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
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
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.berlinpartymap.data.remote.dto.EventDto
import com.example.berlinpartymap.ui.components.EventDetailView
import com.example.berlinpartymap.ui.components.EventListItem
import kotlinx.coroutines.flow.forEach
import androidx.compose.animation.*

//import org.koin.androidx.compose.koinViewModel


@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    mapViewModel: MapViewModel = viewModel() // ViewModel für Daten (z.B. Events)
) {

    val events by mapViewModel.events.collectAsState()
    val clubs by mapViewModel.clubs.collectAsState()

    var mapListToggle by remember { mutableStateOf(true) }

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


    //--------Inhalt des unteren Behälters---------
    var selectedEvent by remember { mutableStateOf<EventDto?>(null) }
    var eventSelected by remember { mutableStateOf<Boolean>(false) }

    if (selectedEvent != null) {
        BackHandler {
            eventSelected = false
            selectedEvent = null
        }
    }

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
                        .padding(top = 32.dp, bottom = 16.dp, start = 8.dp, end = 8.dp)
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
                            .padding(top = 32.dp, bottom = 16.dp, start = 8.dp, end = 8.dp)
                            .fillMaxWidth()
                            .height(mapHeight),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(0f)),
                        onClick = { mapListToggle = !mapListToggle }
                    ) {

                    }
                }

            }

            // -------- LISTE-DETAIL-CONTAINER ----------

            Box(modifier = Modifier.height(listHeight)) {
                Column (
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth()
                        .height(listHeight)
                        .shadow(
                            shape = RoundedCornerShape(10.dp),
                            elevation = animatedListElevation,
                            spotColor = Color.White,
                            ambientColor = Color.White
                        )
                        .background(
                            shape = RoundedCornerShape(15.dp),
                            color = Color.Black.copy(0.97f)
                        )
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(15.dp)
                        )
                        //.border = BorderStroke(width = 1.dp, color = Color.White),
                    //,
                    //colors = CardDefaults.cardColors(Color.Black.copy(0.97f))
                ) {
                    AnimatedContent(
                        targetState = eventSelected,
                        transitionSpec = {
                            // Logik: Gehen wir ZU den Details (true) oder ZURÜCK zur Liste (false)?
                            if (targetState) {
                                // Hineingehen: Details kommen von rechts (width), Liste geht nach links (-width)
                                slideInHorizontally { width -> width } + fadeIn() togetherWith
                                        slideOutHorizontally { width -> -width } + fadeOut()
                            } else {
                                // Zurückgehen: Liste kommt von links (-width), Details gehen nach rechts (width)
                                slideInHorizontally { width -> -width } + fadeIn() togetherWith
                                        slideOutHorizontally { width -> width } + fadeOut()
                            }.using(
                                // Sorgt dafür, dass der Inhaltswechsel nicht durch Clipping abgeschnitten wird
                                SizeTransform(clip = false)
                            )
                        },
                        label = "contentTransform"
                    ) { selection ->
                        if (!selection) {
                            LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
                                items(events) { event ->
                                    EventListItem(event = event, onClick = {
                                        eventSelected = true
                                        selectedEvent = event
                                        mapView.controller.animateTo(GeoPoint(event.latitude, event.longitude))
                                        mapView.controller.setZoom(17.0)
                                    })
                                }
                            }
                        } else {
                            if (selectedEvent != null) {
                                EventDetailView(event = selectedEvent!!, onClick = {
                                    eventSelected = false
                                    selectedEvent = null
                                })
                            } else {
                                eventSelected = false
                            }
                        }
                    }

                }
                if (mapListToggle) {
                    Card(
                        modifier = Modifier
                            //.padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
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