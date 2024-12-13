package nl.q42.template.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import nl.q42.template.ui.compose.composables.widgets.AppSurface

/**
 * Generate this using: https://m2.material.io/material-theme-builder/. For more info and resources: see the
 * README file of our project.
 */

private val LocalAppTypography = staticCompositionLocalOf { AppTypography() }
private val LocalAppColorTokens = staticCompositionLocalOf<AppColorTokens> {
    // Dummy default, will be replaced for the actual tokens by the Provider
    AppColorTokensLight
}
private val LocalAppShapes = staticCompositionLocalOf { AppShapes() }

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: AppTypography = AppTheme.typography,
    colors: AppColorTokens = AppTheme.colors,
    shapes: AppShapes = AppTheme.shapes,

    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.accent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    CompositionLocalProvider(
        LocalAppTypography provides typography,
        LocalAppColorTokens provides if (darkTheme) AppColorTokensDark else AppColorTokensLight,
        LocalAppShapes provides shapes,
        /** configures the ripple for material components */
        LocalRippleConfiguration provides AppRippleConfiguration,
        /** needed for non-material components to have a material ripple. eg [Modifier.clickable] */
        LocalIndication provides AppRipple,
        /** merges the platform style with our type, @see [ProvideTextStyle] for more context */
        LocalTextStyle provides LocalTextStyle.current.merge(typography.body),
        LocalContentColor provides colors.textPrimary,
        content = content
    )
}

object AppTheme {
    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current
    val colors: AppColorTokens
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColorTokens.current
    val shapes: AppShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShapes.current
}

@Immutable
data class AppShapes(
    val small: Shape = RoundedCornerShape(Dimens.Containers.cornerRadius),
    val medium: Shape = RoundedCornerShape(Dimens.Containers.cornerRadius),
    val large: Shape = RoundedCornerShape(Dimens.Containers.cornerRadiusLarge)
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PreviewAppTheme(content: @Composable () -> Unit) {
    AppTheme {
        Scaffold {
            AppSurface {
                content()
            }
        }
    }
}