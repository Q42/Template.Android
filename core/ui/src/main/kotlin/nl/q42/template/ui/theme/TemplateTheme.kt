package nl.q42.template.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import nl.q42.template.ui.compose.composables.widgets.TemplateSurface

/**
 * Generate this using: https://m2.material.io/material-theme-builder/. For more info and resources: see the
 * README file of our project.
 */

private val LocalTemplateTypography = staticCompositionLocalOf { TemplateTypography() }
private val LocalTemplateColorScheme = staticCompositionLocalOf<TemplateColorScheme> {
    // Dummy default, will be replaced for the actual tokens by the Provider
    TemplateColorSchemeLight
}
private val LocalTemplateShapes = staticCompositionLocalOf { TemplateShapes() }

@Composable
fun TemplateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: TemplateTypography = TemplateTheme.typography,
    colors: TemplateColorScheme = TemplateTheme.colors,
    shapes: TemplateShapes = TemplateTheme.shapes,
    content: @Composable () -> Unit
) {
    // status bar color
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.accent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    CompositionLocalProvider(
        LocalTemplateTypography provides typography,
        LocalTemplateColorScheme provides if (darkTheme) TemplateColorSchemeDark else TemplateColorSchemeLight,
        LocalTemplateShapes provides shapes,
        /** configures the ripple for material components */
        LocalRippleConfiguration provides TemplateRippleConfiguration,
        /** needed for non-material components to have a material ripple. eg [Modifier.clickable] */
        LocalIndication provides TemplateRipple,
        /** merges the platform style with our type, @see [ProvideTextStyle] for more context */
        LocalTextStyle provides LocalTextStyle.current.merge(typography.body),
        LocalContentColor provides colors.textPrimary,
        content = content
    )
}

object TemplateTheme {
    val typography: TemplateTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTemplateTypography.current
    val colors: TemplateColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalTemplateColorScheme.current
    val shapes: TemplateShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalTemplateShapes.current
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PreviewTemplateTheme(content: @Composable () -> Unit) {
    TemplateTheme {
        Scaffold {
            TemplateSurface {
                content()
            }
        }
    }
}