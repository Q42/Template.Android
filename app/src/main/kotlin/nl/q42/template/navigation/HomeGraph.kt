package nl.q42.template.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import nl.q42.template.home.main.presentation.HomeViewModel
import nl.q42.template.home.main.ui.HomeScreen
import nl.q42.template.home.second.presentation.HomeSecondViewModel
import nl.q42.template.home.second.ui.HomeSecondScreen
import nl.q42.template.navigation.viewmodel.InitNavigator

internal fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    appDeepLinkScheme: String,
) {
    navigation<Destination.HomeGraph>(startDestination = Destination.Home) {
        composable<Destination.Home> {

            val viewModel: HomeViewModel = hiltViewModel()
            InitNavigator(navController = navController, routeNavigator = viewModel)

            HomeScreen(viewModel = viewModel)
        }
        composable<Destination.HomeSecond>(
            deepLinks = listOf(
                // keep in sync with Destinations.HomeSecond:
                // title should be the name of a parameter of Destinations.HomeSecond
                navDeepLink { uriPattern = "$appDeepLinkScheme://home/second/{title}" }
            )
        ) {

            val viewModel: HomeSecondViewModel = hiltViewModel()
            InitNavigator(navController = navController, routeNavigator = viewModel)

            HomeSecondScreen(viewModel = viewModel)
        }
    }
}