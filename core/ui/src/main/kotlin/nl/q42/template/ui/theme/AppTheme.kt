package nl.q42.template.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import nl.q42.template.ui.compose.composables.widgets.AppSurface

/**
 * Generate this using: https://m2.material.io/material-theme-builder/. For more info and resources: see the
 * README file of our project.
 */

private val LocalAppTypography = staticCompositionLocalOf { AppTypography() }
private val LocalAppColorScheme = staticCompositionLocalOf<AppColorScheme> {
    // Dummy default, will be replaced for the actual tokens by the Provider
    AppColorSchemeLight
}
private val LocalAppShapes = staticCompositionLocalOf { AppShapes() }

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: AppTypography = AppTheme.typography,
    colors: AppColorScheme = AppTheme.colors,
    shapes: AppShapes = AppTheme.shapes,
    content: @Composable () -> Unit
) {

    CompositionLocalProvider(
        LocalAppTypography provides typography,
        LocalAppColorScheme provides if (darkTheme) AppColorSchemeDark else AppColorSchemeLight,
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
    val colors: AppColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColorScheme.current
    val shapes: AppShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShapes.current
}

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