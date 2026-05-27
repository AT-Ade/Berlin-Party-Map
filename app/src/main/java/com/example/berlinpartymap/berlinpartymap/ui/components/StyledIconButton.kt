package com.example.berlinpartymap.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun StyledIconButton(
    modifier: Modifier = Modifier, // Nimmt jetzt die Animationen auf
    onClick: () -> Unit,
    icon: ImageVector,
    iconColor: Color,
    iconWidth: Dp = 30.dp,
    iconHeight: Dp = 30.dp
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.DarkGray.copy(0.7f),
            contentColor = iconColor
        ),
        contentPadding = PaddingValues(0.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray.copy(0.3f)
        ),
        // WICHTIG: Verkettet den reingereichten Modifier mit der festen Größe!
        modifier = modifier.size(height = 40.dp, width = 40.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Button Icon",
            modifier = Modifier.size(height = iconHeight, width = iconWidth)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true )
@Composable
private fun StyledIconButtonPreview() {
    // Use Theme here
    StyledIconButton(
        onClick = {},
        icon = Icons.Rounded.Star,
        iconColor = Color.Yellow
        )
}