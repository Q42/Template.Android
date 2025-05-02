package nl.q42.template.navigation

import kotlinx.serialization.Serializable

/**
 * All destinations that can be navigated to. Use these in your ViewModel, whenever you
 * want to navigate. Note that you can only navigate to a destination from the correct graph,
 * see [nl.q42.template.navigation.homeGraph].
 * For deeplink support, add a deep link to the destination in the graph.
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
    // all parameters should be path parameters of a deeplink in HomeGraph.kt: composable<Destination.HomeSecond>(deeplinks = listOf(...))
    data class HomeSecond(val title: String) : Destination()

    @Serializable
    data object Onboarding : Destination()
}
