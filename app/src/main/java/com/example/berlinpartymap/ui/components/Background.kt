package com.example.berlinpartymap.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.berlinpartymap.R

@Composable
fun Background() {
    Image(
        painter = painterResource(id = R.drawable.blacklightning),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@Preview(showBackground = true)
@Composable
private fun BackgroundPreview() {
    // Use Theme here
    Background()
}