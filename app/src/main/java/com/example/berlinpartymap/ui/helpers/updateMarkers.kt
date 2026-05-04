package com.example.berlinpartymap.ui.helpers

import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.berlinpartymap.data.remote.dto.EventDto
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

fun updateMarkers(
    mapView: MapView,
    events: List<EventDto>,
    onMarkerClick: (EventDto) -> Unit = {}
) {
    // 1. Alte Marker entfernen
    mapView.overlays.clear()

    val context = mapView.context

    events.forEachIndexed { index, event ->

        val marker = Marker(mapView).apply {
            // 📍 Position setzen
            position = GeoPoint(event.latitude, event.longitude)

            // 🏷️ Texte setzen
            title = event.venueName
            snippet = "Tap für Details"

            // 🎨 FARBE ANPASSEN (Der einfache Weg)
            // Wir nehmen das Standard-Icon, das der Marker automatisch hat
            val iconDrawable = icon?.mutate()

            // Hier kannst du die Farbe wählen.
            // Beispiel: Jeder zweite Marker ist Magenta, sonst Blau.
            val color = if (index % 2 == 0) {
                Color.WHITE // Magenta
            } else {
                Color.parseColor("#F0BCD4") // Cyan/Blau
            }

            iconDrawable?.setTint(color)
            icon = iconDrawable

            // ⚓ Anchor: Die Spitze des Pins soll auf die Koordinate zeigen
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

            // 👁️ Transparenz (optional, wie in deinem Entwurf)
            alpha = if (index % 3 == 0) 0.7f else 1.0f

            // 👉 Klick-Logik
            setOnMarkerClickListener { m, _ ->
                m.showInfoWindow()
                onMarkerClick(event)
                true
            }
        }

        // Marker zur Karte hinzufügen
        mapView.overlays.add(marker)
    }

    // Karte neu zeichnen, um Änderungen anzuzeigen
    mapView.invalidate()
}