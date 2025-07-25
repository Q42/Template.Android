package nl.q42.template.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val defaultFontFamily = FontFamily.Default

@Immutable
data class AppTypography(
    val body: TextStyle = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    val h1: TextStyle = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    val label: TextStyle = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
