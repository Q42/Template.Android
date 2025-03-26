package nl.q42.template.navigation

import kotlinx.serialization.Serializable

val fullscreenDestinations = listOf(
    Destination.Onboarding::class
)

/**
 * All destinations that can be navigated to. Use these in your ViewModel, whenever you
 * want to navigate. Note that you can only navigate to a destination from the correct graph,
 * see [com.aressfinancial.creditapp.navigation.graph].
 */
@Serializable
sealed class Destination {

    /**
     * Main destination. If you add a bottom navigation component, make a graph per bottom tab.
     */
    @Serializable
    data object HomeGraph : Destination()

    @Serializable
    data object Home : Destination()

    @Serializable
    data class HomeSecond(val title: String) : Destination()

    @Serializable
    data object ProfileGraph : Destination()

    @Serializable
    data object Profile : Destination()

    @Serializable
    data object Onboarding : Destination()
}
