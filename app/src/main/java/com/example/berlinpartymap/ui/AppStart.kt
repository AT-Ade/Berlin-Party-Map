package com.example.berlinpartymap.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.berlinpartymap.data.enums.TabItem
import com.example.berlinpartymap.ui.map.MapScreen
import com.example.berlinpartymap.ui.partyhistory.PartyHistoryScreenScreen
import com.example.berlinpartymap.ui.savedevents.SavedEventsScreen
import kotlinx.serialization.Serializable

@Serializable
object MapRoute

@Serializable
object SavedRoute

@Serializable
object HistoryRoute

@Composable
fun AppStart(
    modifier: Modifier
) {

    val navController = rememberNavController()
    var selectedTab by rememberSaveable { mutableStateOf(TabItem.MAP) }


    Scaffold(
        bottomBar = {
            NavigationBar {
                TabItem.entries.forEach { tabItem ->
                    NavigationBarItem(
                        selected = selectedTab == tabItem,
                        onClick = { selectedTab = tabItem },

                        icon = {
                            Icon(
                                imageVector = tabItem.tabIcon,
                                contentDescription = "TabItem"
                            )
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
            startDestination = selectedTab.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<MapRoute>{
                MapScreen()
            }
            composable<SavedRoute> {
                SavedEventsScreen()
            }

            composable<HistoryRoute> {
                PartyHistoryScreenScreen()
            }

        }
    }
}
