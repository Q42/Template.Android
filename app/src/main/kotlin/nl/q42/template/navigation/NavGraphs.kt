package nl.q42.template.navigation

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import nl.q42.template.ui.home.destinations.HomeScreenDestination
import nl.q42.template.ui.home.destinations.HomeSecondScreenDestination

/**
 *
 * Define your graphs here, since app knows all feature modules.
 *
 * More info: https://composedestinations.rafaelcosta.xyz/codegenconfigs/#multi-module-configs
 */
object NavGraphs {

    val home = object : NavGraphSpec {
        override val route = "home"

        override val startRoute = HomeScreenDestination

        override val destinationsByRoute = listOf<DestinationSpec<*>>(
            HomeScreenDestination,
            HomeSecondScreenDestination,
        )
            .associateBy { it.route }
    }

    val root = object : NavGraphSpec {
        override val route = "root"
        override val startRoute = home
        override val destinationsByRoute = emptyMap<String, DestinationSpec<*>>()
        override val nestedNavGraphs = listOf(
            home,
        )
    }
}