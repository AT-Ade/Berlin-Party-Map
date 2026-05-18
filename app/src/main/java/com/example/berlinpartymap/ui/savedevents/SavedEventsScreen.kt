package com.example.berlinpartymap.ui.savedevents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//import org.koin.androidx.compose.koinViewModel


@Composable
fun SavedEventsScreenScreen(
    //savedEventsScreenViewModel: SavedEventsScreenViewModel = koinViewModel()
) {
    //val uiState by savedEventsScreenViewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SavedEventsScreenScreenPreview() {
    SavedEventsScreenScreen()
}