package nl.q42.template.ui.compose.composables.widgets

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import nl.q42.template.ui.theme.PreviewLightDark
import nl.q42.template.ui.theme.TemplateTheme

@Composable
fun TemplateButton(text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = TemplateTheme.colors.accent,
            contentColor = TemplateTheme.colors.buttonText,
            disabledContentColor = TemplateTheme.colors.buttonText.copy(alpha = 0.5f),
            disabledContainerColor = TemplateTheme.colors.accent.copy(alpha = 0.5f)
        )
    ) {
        Text(
            text = text,
            style = TemplateTheme.typography.body,
            color = TemplateTheme.colors.buttonText
        )
    }
}

@Composable
@PreviewLightDark
private fun TemplateButtonPreview() {
    TemplateTheme {
        TemplateButton("Button",) {}
    }
}