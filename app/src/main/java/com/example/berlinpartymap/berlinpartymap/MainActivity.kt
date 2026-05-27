package com.example.berlinpartymap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.berlinpartymap.ui.AppStart
import com.example.berlinpartymap.ui.components.Background
import com.example.berlinpartymap.ui.map.MapScreen
import com.example.berlinpartymap.ui.theme.MyApplicationTheme
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // ---------OpenStreetMap configuration---------

        Configuration.getInstance().load(
            applicationContext,
            applicationContext.getSharedPreferences("osm_prefs", MODE_PRIVATE)
        )
        Configuration.getInstance().userAgentValue = packageName

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Box(Modifier.fillMaxSize()) {
                    Background()
                    Scaffold(
                        containerColor = androidx.compose.ui.graphics.Color.Black.copy(0.9f),
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        AppStart(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
    }
}