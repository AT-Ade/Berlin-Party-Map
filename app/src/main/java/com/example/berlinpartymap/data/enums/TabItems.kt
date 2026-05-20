package com.example.berlinpartymap.data.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.berlinpartymap.ui.HistoryRoute
import com.example.berlinpartymap.ui.MapRoute
import com.example.berlinpartymap.ui.SavedRoute

enum class TabItem(
    val route: Any,
    val tabTitle: String,
    val tabIcon: ImageVector
) {
    MAP(MapRoute, "Karte", Icons.Rounded.Map),
    FAVOURITES(SavedRoute, "Favoriten", Icons.Rounded.Star),
    HISTORY(HistoryRoute, "Verlauf", Icons.Rounded.History),
    //...
}
