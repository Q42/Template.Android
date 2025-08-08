package nl.q42.template.ui.compose.composables.window

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import nl.q42.template.ui.compose.get
import nl.q42.template.ui.presentation.SnackBarSpec
import nl.q42.template.ui.theme.AppTheme
import nl.q42.template.ui.theme.Dimens
import nl.q42.template.ui.theme.PreviewAll
import java.util.UUID

@Composable
fun AppSnackbarHost(snackbarHostState: SnackbarHostState) {
    SnackbarHost(snackbarHostState) { data ->
        SnackBar(data)
    }
}

@Composable
private fun SnackBar(
    data: SnackbarData
) {
    val isError = (data.visuals as? AppSnackBarVisuals)?.isError ?: false
    val appColors = AppTheme.colors
    val contentColor = if (isError) appColors.errorContent else appColors.textSecondary
    val buttonColors = ButtonDefaults.textButtonColors(contentColor = contentColor)

    Snackbar(
        modifier = Modifier.padding(Dimens.screenPaddingHorizontal),
        contentColor = contentColor,
        containerColor = if (isError) appColors.error else appColors.surfaceSecondary,
        action = {
            TextButton(
                onClick = { if (isError) data.dismiss() else data.performAction() },
                colors = buttonColors
            ) {
                Text(
                    text = data.visuals.actionLabel ?: "",
                    style = AppTheme.typography.body,
                )
            }
        }
    ) {
        Text(
            text = data.visuals.message,
            style = AppTheme.typography.label,
        )
    }
}

@Composable
fun SnackBarSpec.toSnackBarVisuals() = AppSnackBarVisuals(
    message = message.get(),
    actionLabel = actionLabel ?: "",
    withDismissAction = withDismissAction,
    isError = isError,
    id = id
)

@PreviewAll
@Composable
private fun SnackBarPreview() {
    val data = object : SnackbarData {
        override val visuals: SnackbarVisuals
            get() = AppSnackBarVisuals("This is a snackbar", "Action", isError = false)

        override fun dismiss() {}

        override fun performAction() {}
    }

    AppTheme {
        SnackBar(data = data)
    }
}

@Composable
@PreviewLightDark
fun SnackBarPreviewError() {
    val data = object : SnackbarData {
        override val visuals: SnackbarVisuals
            get() = AppSnackBarVisuals(
                "This is an error snackbar with very long text",
                isError = true
            )

        override fun dismiss() {}

        override fun performAction() {}
    }

    AppTheme {
        SnackBar(data = data)
    }
}

class AppSnackBarVisuals(
    override val message: String,
    override val actionLabel: String? = "",
    override val duration: SnackbarDuration = SnackbarDuration.Indefinite,
    override val withDismissAction: Boolean = false,
    val isError: Boolean,
    @Suppress("unused") val id: String = UUID.randomUUID()
        .toString() // used to make every Snackbar unique so we can send the same message twice
) : SnackbarVisuals
