package nl.q42.template.ui.theme

import androidx.compose.foundation.Indication
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

val AppRippleConfiguration: RippleConfiguration
    @Composable
    @ReadOnlyComposable
    get() = RippleConfiguration(
        color = AppTheme.colors.surfaceSelected
    )

val NoRippleConfiguration: RippleConfiguration
    @Composable
    @ReadOnlyComposable
    get() = RippleConfiguration(
        color = Color.Transparent,
        rippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
    )

val AppRipple: Indication
    @Composable
    @ReadOnlyComposable
    get() = ripple(
        color = AppTheme.colors.surfaceSelected
    )
