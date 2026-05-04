package com.example.berlinpartymap.ui.map

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.*
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.berlinpartymap.R
import com.example.berlinpartymap.data.remote.dto.EventDto
import com.example.berlinpartymap.ui.components.Background
import com.example.berlinpartymap.ui.components.EventDetailView
import com.example.berlinpartymap.ui.components.EventListItem
import com.example.berlinpartymap.ui.helpers.updateMarkers
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    mapViewModel: MapViewModel = viewModel()
) {

    val events by mapViewModel.events.collectAsState()
    val selectedEvent by mapViewModel.selectedEvent.collectAsState()
    val eventSelected by mapViewModel.eventSelected.collectAsState()

    var mapListToggle by remember { mutableStateOf(true) }

    // -------- Animationen --------
    val mapHeight by animateDpAsState(
        targetValue = if (mapListToggle) 600.dp else 200.dp,
        animationSpec = tween(200), label = ""
    )

    val listHeight by animateDpAsState(
        targetValue = if (mapListToggle) 200.dp else 600.dp,
        animationSpec = tween(200), label = ""
    )

    val mapElevation by animateDpAsState(
        targetValue = if (mapListToggle) 20.dp else 5.dp, label = ""
    )

    val listElevation by animateDpAsState(
        targetValue = if (!mapListToggle) 20.dp else 5.dp, label = ""
    )

    // Back Navigation für Detail View
    if (selectedEvent != null) {
        BackHandler {
            mapViewModel.clearSelection()
        }
    }

    // -------- MapView Setup --------
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val mapView = remember { MapView(context) }

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                else -> {}
            }
        }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }

    // -------- Daten laden --------
    LaunchedEffect(Unit) {
        mapViewModel.loadInitialData()
    }

    // -------- Marker setzen --------
    LaunchedEffect(events) {
        updateMarkers(mapView, events)
    }

    // -------- UI --------
    Box {
        Background()

        Column {

            MapContainer(
                mapView = mapView,
                mapHeight = mapHeight,
                elevation = mapElevation,
                mapListToggle = mapListToggle,
                onToggle = { mapListToggle = !mapListToggle }
            )

            EventContainer(
                events = events,
                selectedEvent = selectedEvent,
                eventSelected = eventSelected,
                listHeight = listHeight,
                elevation = listElevation,
                mapListToggle = mapListToggle,
                onToggle = { mapListToggle = !mapListToggle },
                onEventClick = {
                    mapViewModel.selectEvent(it)
                    mapView.controller.animateTo(GeoPoint(it.latitude, it.longitude))
                    mapView.controller.setZoom(17.0)
                },
                onBack = { mapViewModel.clearSelection() }
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MapScreenPreview() {
    MapScreen()
}