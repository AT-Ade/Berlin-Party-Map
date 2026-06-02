package com.example.berlinpartymap.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.berlinpartymap.data.enums.TabItem
import com.example.berlinpartymap.ui.map.MapScreen
import com.example.berlinpartymap.ui.partyhistory.HistoryDetailScreen
import com.example.berlinpartymap.ui.partyhistory.PartyHistoryScreen
import com.example.berlinpartymap.ui.partyhistory.PartyHistoryViewModel
import com.example.berlinpartymap.ui.savedevents.SavedEventDetailScreen
import com.example.berlinpartymap.ui.savedevents.SavedEventsScreen
import com.example.berlinpartymap.ui.savedevents.SavedEventsViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object MapRoute

@Serializable
object SavedRoute

// Detail-Route für Favoriten: enthält die eventId als Pfad-Parameter
@Serializable
data class SavedDetailRoute(val eventId: String)

@Serializable
object HistoryRoute


@Serializable
data class HistoryDetailRoute(val eventId: String)

@Composable
fun AppStart(
    modifier: Modifier
) {

    val navController = rememberNavController()

    // Aktuellen Back-Stack beobachten, um den aktiven Tab zu ermitteln
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // ViewModels einmalig hier erstellen und an Child-Screens weitergeben,
    // damit Badge-Count und isSaved-Prüfung auf denselben StateFlows arbeiten
    val savedEventsViewModel: SavedEventsViewModel = koinViewModel()
    val partyHistoryViewModel: PartyHistoryViewModel = koinViewModel()

    // Anzahl der vergangenen Events, die noch nicht als besucht/nicht-besucht markiert wurden
    val pendingConfirmationCount by partyHistoryViewModel.pendingConfirmationCount.collectAsState()

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            NavigationBar (

                containerColor = Color.Black.copy(alpha = 0.5f),

                tonalElevation = 0.dp
            ){

                TabItem.entries.forEach { tabItem ->

                    val isSelected = when (tabItem) {
                        TabItem.MAP -> currentRoute?.contains("MapRoute") == true
                        TabItem.FAVOURITES -> currentRoute?.contains("SavedRoute") == true ||
                                currentRoute?.contains("SavedDetailRoute") == true
                        TabItem.HISTORY -> currentRoute?.contains("HistoryRoute") == true ||
                                currentRoute?.contains("HistoryDetailRoute") == true
                    }

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            navController.navigate(tabItem.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            // History-Tab bekommt einen Badge mit der Anzahl unbestätigter Events
                            if (tabItem == TabItem.HISTORY && pendingConfirmationCount > 0) {
                                BadgedBox(badge = {
                                    Badge { Text(pendingConfirmationCount.toString()) }
                                }) {
                                    Icon(
                                        imageVector = tabItem.tabIcon,
                                        contentDescription = "TabItem"
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = tabItem.tabIcon,
                                    contentDescription = "TabItem"
                                )
                            }
                        },
                        label = {
                            Text(tabItem.tabTitle)
                        }
                    )

                }
            }
        }

    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = MapRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<MapRoute>{
                MapScreen(savedEventsViewModel = savedEventsViewModel)
            }

            composable<SavedRoute> {
                SavedEventsScreen(
                    viewModel = savedEventsViewModel,
                    onEventClick = { eventId -> navController.navigate(SavedDetailRoute(eventId)) }
                )
            }

            // Detail-Ansicht für ein favorisiertes Event
            composable<SavedDetailRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<SavedDetailRoute>()
                SavedEventDetailScreen(
                    eventId = route.eventId,
                    viewModel = savedEventsViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable<HistoryRoute> {
                PartyHistoryScreen(
                    viewModel = partyHistoryViewModel,
                    onEventClick = { eventId -> navController.navigate(HistoryDetailRoute(eventId)) }
                )
            }

            // Detail-Ansicht für ein besuchtes Event
            composable<HistoryDetailRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<HistoryDetailRoute>()
                HistoryDetailScreen(
                    eventId = route.eventId,
                    viewModel = partyHistoryViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

        }
    }
}
