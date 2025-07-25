package nl.q42.template.ui.compose.composables.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import nl.q42.template.core.ui.R
import nl.q42.template.ui.compose.get
import nl.q42.template.ui.presentation.ViewStateString
import nl.q42.template.ui.presentation.dialog.DialogData
import nl.q42.template.ui.theme.PreviewTemplateTheme
import nl.q42.template.ui.theme.TemplateTheme

@Composable
fun Dialog(
    data: DialogData,
    onDialogDismissed: (Any) -> Unit,
    onDialogConfirmed: (Any) -> Unit,
) {

    val contentColor: Color = TemplateTheme.colors.textPrimary

    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onDismissRequest.
            onDialogDismissed(data.tag)
        },
        title = data.title?.let {
            {
                Text(
                    text = it.get(),
                    color = contentColor
                )
            }
        },
        text = {
            Text(
                text = data.description.get(),
                color = contentColor
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDialogConfirmed(data.tag)
                }
            ) {
                Text(
                    text = data
                        .confirmButtonTitle?.get()
                        ?: stringResource(id = R.string.generic_ok),
                    color = contentColor
                )
            }
        },
        dismissButton = data.dismissButtonTitle?.let { dismissButton ->
            {
                TextButton(onClick = { onDialogDismissed(data.tag) }) {
                    Text(
                        text = dismissButton.get(),
                        color = contentColor
                    )
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun DialogPreview() {
    PreviewTemplateTheme {
        Dialog(
            data = DialogData(
                title = ViewStateString.Basic("Title"),
                description = ViewStateString.Basic("Multiline description\nwith new line"),
                confirmButtonTitle = ViewStateString.Basic("OK"),
                dismissButtonTitle = ViewStateString.Basic("Cancel")
            ),
            onDialogDismissed = {},
            onDialogConfirmed = {}
        )
    }
}
