package nl.q42.template.ui.compose.composables.widgets

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.q42.template.ui.theme.PreviewLightDark
import nl.q42.template.ui.theme.TemplateTheme

@Composable
fun TemplateSurface(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Surface(
        modifier = modifier,
        color = TemplateTheme.colors.surface,
        contentColor = TemplateTheme.colors.textPrimary,
        content = content
    )
}

@Composable
@PreviewLightDark
private fun TemplateSurfacePreview() {
    TemplateTheme {
        TemplateSurface(Modifier.fillMaxSize()) {}
    }
}
