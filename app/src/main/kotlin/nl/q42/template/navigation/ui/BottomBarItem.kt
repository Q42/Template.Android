package nl.q42.template.navigation.ui

import androidx.annotation.StringRes
import nl.q42.template.core.ui.R
import nl.q42.template.navigation.Destination
import nl.q42.template.ui.resource.DrawableResource
import nl.q42.template.R as AppR

enum class BottomBarItem(
    val destination: Destination,
    val icon: DrawableResource,
    @StringRes val label: Int
) {
    Home(
        destination = Destination.HomeGraph,
        icon = DrawableResource(AppR.drawable.ic_launcher_foreground),
        label = R.string.bottom_bar_home
    ),
    Profile(
        destination = Destination.ProfileGraph,
        icon = DrawableResource(AppR.drawable.ic_launcher_foreground),
        label = R.string.bottom_bar_profile
    ),
}
