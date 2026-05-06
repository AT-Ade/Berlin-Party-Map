package com.example.berlinpartymap.ui.helpers

import android.R.attr.text
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
 * Erstellt einen Marker-Pin mit variabler Größe.
 */
fun createMarkerIcon(
    context: Context,
    color: Color,
    sizeDp: Int = 44,
    text: String? = null
    )
: BitmapDrawable {
    val density = context.resources.displayMetrics.density
    val size = (sizeDp * density).toInt()
    val strokeWidth = (2 * density)
    val shadowRadius = (size * 0.2f) // Schatten proportional zur Größe

    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val center = size / 2f
    val radius = (size / 2f) - shadowRadius

    // Schatten
    paint.color = android.graphics.Color.WHITE
    paint.alpha = 50
    paint.setShadowLayer(shadowRadius, 0f, 0f, android.graphics.Color.WHITE)
    canvas.drawCircle(center, center, radius, paint)
    paint.clearShadowLayer()

    // Kreis
    paint.color = color.toArgb()
    paint.alpha = 255
    canvas.drawCircle(center, center, radius, paint)

    // Rand
    paint.color = android.graphics.Color.BLACK
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = strokeWidth
    canvas.drawCircle(center, center, radius, paint)

    // Text zeichnen
    if (!text.isNullOrEmpty()) {
        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.color = android.graphics.Color.BLACK // Textfarbe
            this.textSize = radius * 0.25f // Textgröße proportional zum Kreis
            this.textAlign = Paint.Align.CENTER
            this.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        // Text vertikal zentrieren
        val textBounds = Rect()
        textPaint.getTextBounds(text, 0, text.length, textBounds)
        val textHeight = textBounds.height()

        canvas.drawText(text, center, center + (textHeight / 2f), textPaint)
    }

    return BitmapDrawable(context.resources, bitmap)
}

fun updateMarkers(
    mapView: MapView,
    events: List<EventDto>,
    highlightedEvent: EventDto?, // Neu hinzugefügt
    onMarkerClick: (EventDto) -> Unit = {}
) {
    mapView.overlays.clear()
    val context = mapView.context


    events.forEach { event ->
        val isHighlighted = event == highlightedEvent

        val size = if (isHighlighted) 250 else 44
        val label = if (isHighlighted) event.venueName else null

        val markerIcon = createMarkerIcon(
            context = context,
            color = Color.White,
            sizeDp = size,
            text = label // Wird nur übergeben, wenn hervorgehoben
        )

        val marker = Marker(mapView).apply {
            position = GeoPoint(event.latitude, event.longitude)
            icon = markerIcon
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
            infoWindow = null

            setOnMarkerClickListener { _, _ ->
                onMarkerClick(event)
                true
            }
        }
        mapView.overlays.add(marker)
    }
    mapView.invalidate()
}