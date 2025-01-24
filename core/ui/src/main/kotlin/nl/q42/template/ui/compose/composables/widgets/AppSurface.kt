package nl.q42.template.ui.compose.composables.widgets

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.q42.template.ui.theme.AppTheme
import nl.q42.template.ui.theme.PreviewLightDark

@Composable
fun AppSurface(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Surface(
        modifier = modifier,
        color = AppTheme.colors.surface,
        contentColor = AppTheme.colors.textPrimary,
        content = content
    )
}

@Composable
@PreviewLightDark
private fun AppSurfacePreview() {
    AppTheme {
        AppSurface(Modifier.fillMaxSize()) {}
    }
}
