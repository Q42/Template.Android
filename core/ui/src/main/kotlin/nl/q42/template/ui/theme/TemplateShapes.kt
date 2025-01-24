package nl.q42.template.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Immutable
data class TemplateShapes(
    val small: Shape = RoundedCornerShape(8.dp),
    val medium: Shape = RoundedCornerShape(16.dp),
    val large: Shape = RoundedCornerShape(20.dp)
)