package nl.q42.template.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import nl.q42.template.navigation.viewmodel.InitNavigator
import nl.q42.template.onboarding.start.presentation.OnboardingStartViewModel
import nl.q42.template.onboarding.start.ui.OnboardingStartScreen

internal fun NavGraphBuilder.onboardingDestinations(navController: NavHostController) {
    composable<Destination.Onboarding> {

        val viewModel: OnboardingStartViewModel = hiltViewModel()
        InitNavigator(navController = navController, viewModel)

        OnboardingStartScreen(viewModel = viewModel)
    }
}
