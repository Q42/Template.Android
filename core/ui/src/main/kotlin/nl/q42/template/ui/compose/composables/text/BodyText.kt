package nl.q42.template.ui.compose.composables.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import nl.q42.template.ui.theme.AppTheme
import nl.q42.template.ui.theme.PreviewLightDark

@Composable
fun BodyText(text: String, color: Color = AppTheme.colors.textPrimary) {
    Text(
        text = text,
        color = color,
        style = AppTheme.typography.body
    )
}

@Composable
@PreviewLightDark
private fun BodyTextPreview() {
    AppTheme {
        BodyText("Body text")
    }
}