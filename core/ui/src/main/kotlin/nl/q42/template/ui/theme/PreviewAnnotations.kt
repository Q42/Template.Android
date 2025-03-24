package nl.q42.template.ui.theme

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale

@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FUNCTION
)
@Preview(name = "85% Dark", fontScale = 0.85f, uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)
@Preview(name = "100% Light", fontScale = 1f, uiMode = UI_MODE_TYPE_NORMAL)
@Preview(name = "150%", fontScale = 1.5f)
@Preview(name = "200%", fontScale = 2f)
annotation class PreviewAll