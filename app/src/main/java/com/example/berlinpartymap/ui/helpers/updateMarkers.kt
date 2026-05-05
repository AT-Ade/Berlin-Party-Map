package com.example.berlinpartymap.ui.helpers

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.berlinpartymap.data.remote.dto.EventDto
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

/**
 * Erstellt einen Marker-Pin mit Schatten und Rand direkt als Bitmap.
 */
fun createMarkerIcon(context: Context, color: Color): BitmapDrawable {
    val density = context.resources.displayMetrics.density
    val size = (44 * density).toInt() // Gesamtgröße inkl. Schatten
    val strokeWidth = (2 * density)
    val shadowRadius = (10 * density)

    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val center = size / 2f
    val radius = (size / 2f) - shadowRadius

    // 1. Schatten zeichnen
    paint.color = android.graphics.Color.WHITE
    paint.alpha = 50 // Transparenz des Schattens
    paint.setShadowLayer(shadowRadius, 0f, 0f, android.graphics.Color.WHITE)
    canvas.drawCircle(center, center, radius, paint)
    paint.clearShadowLayer()

    // 2. Farbiger Kreis (Hintergrund)
    paint.color = color.toArgb()
    paint.alpha = 255
    canvas.drawCircle(center, center, radius, paint)

    // 3. Rand
    paint.color = android.graphics.Color.BLACK
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = strokeWidth
    canvas.drawCircle(center, center, radius, paint)

    return BitmapDrawable(context.resources, bitmap)
}

fun updateMarkers(
    mapView: MapView,
    events: List<EventDto>,
    onMarkerClick: (EventDto) -> Unit = {}
) {
    mapView.overlays.clear()
    val context = mapView.context

    // Icons einmalig vorbereiten, um Performance zu sparen
    val magentaIcon = createMarkerIcon(context, Color.Magenta)
    val blueIcon = createMarkerIcon(context, Color.Blue)
    val whiteIcon = createMarkerIcon(context, Color.White)
    val blackIcon = createMarkerIcon(context, Color.Black)

    events.forEachIndexed { index, event ->
        val marker = Marker(mapView).apply {
            position = GeoPoint(event.latitude, event.longitude)
            title = event.venueName
            snippet = "Tap für Details"

            // Icon zuweisen
            icon = if (index % 2 == 0) whiteIcon else whiteIcon

            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)

            setOnMarkerClickListener { m, _ ->
                m.showInfoWindow()
                onMarkerClick(event)
                true
            }
        }
        mapView.overlays.add(marker)
    }
    mapView.invalidate()
}