package com.example.gotravels.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val travelApp_caption = Color.DarkGray
val travelApp_divider_color = Color.LightGray
private val travelApp_red = Color(0xFFE30425)
private val travelApp_white = Color.White
private val travelApp_purple_700 = Color(0xFF720D5D)
private val travelApp_purple_800 = Color(0xFF5D1049)
private val travelApp_purple_900 = Color(0xFF4E0D3A)

val travelAppColors = lightColors(
    primary = travelApp_purple_800,
    secondary = travelApp_red,
    surface = travelApp_purple_900,
    onSurface = travelApp_white,
    primaryVariant = travelApp_purple_700
)

val BottomSheetShape = RoundedCornerShape(
    topStart = 20.dp,
    topEnd = 20.dp,
    bottomStart = 0.dp,
    bottomEnd = 0.dp
)

@Composable
fun TavelAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(colors = travelAppColors, typography = travelAppTypography) {
        content()
    }
}