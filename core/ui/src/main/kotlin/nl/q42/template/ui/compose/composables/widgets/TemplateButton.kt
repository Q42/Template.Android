package nl.q42.template.ui.compose.composables.widgets

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import nl.q42.template.ui.theme.AppTheme
import nl.q42.template.ui.theme.PreviewLightDark

@Composable
fun TemplateButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.accent,
            contentColor = AppTheme.colors.buttonText
        )
    ) {
        Text(
            text = text,
            style = AppTheme.typography.body,
            color = AppTheme.colors.buttonText
        )
    }
}

@Composable
@PreviewLightDark
private fun TemplateButtonPreview() {
    AppTheme {
        TemplateButton("Button", {})
    }
}