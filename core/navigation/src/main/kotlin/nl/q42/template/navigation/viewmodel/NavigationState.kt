package nl.q42.template.navigation.viewmodel

import java.util.UUID

sealed class NavigationState {
    data object Idle : NavigationState()
    data class NavigateToRoute(
        val route: String,
        val addToBackStack: Boolean,
        val clearBackStack: Boolean,
        val id: String = UUID.randomUUID().toString()
    ) : NavigationState()

    data class PopToRoute(val staticRoute: String, val id: String = UUID.randomUUID().toString()) : NavigationState()

    data class NavigateUp(val id: String = UUID.randomUUID().toString()) : NavigationState()
}
