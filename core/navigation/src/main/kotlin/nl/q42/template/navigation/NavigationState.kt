package nl.q42.template.navigation

import com.ramcosta.composedestinations.spec.Direction
import java.util.UUID

sealed class NavigationState {
    object Idle : NavigationState()
    data class NavigateToRoute(val routeDestination: Direction, val id: String = UUID.randomUUID().toString()) : NavigationState()
    data class PopToRoute(val staticRoute: String, val id: String = UUID.randomUUID().toString()) : NavigationState()
    data class NavigateUp(val id: String = UUID.randomUUID().toString()) : NavigationState()
}