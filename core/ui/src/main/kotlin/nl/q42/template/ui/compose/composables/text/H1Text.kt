package nl.q42.template.ui.compose.composables.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import nl.q42.template.ui.theme.PreviewLightDark
import nl.q42.template.ui.theme.TemplateTheme

@Composable
fun H1Text(text: String, color: Color = TemplateTheme.colors.textPrimary) {
    Text(
        text = text,
        color = color,
        style = TemplateTheme.typography.h1
    )
}

@Composable
@PreviewLightDark
private fun H1TextPreview() {
    TemplateTheme {
        H1Text("H1 text")
    }
}