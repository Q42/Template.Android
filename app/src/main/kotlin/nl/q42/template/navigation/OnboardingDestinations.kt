package nl.q42.template.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import nl.q42.template.navigation.viewmodel.InitNavigator
import nl.q42.template.onboarding.start.presentation.OnboardingStartViewModel
import nl.q42.template.onboarding.start.ui.OnboardingStartScreen
import org.koin.androidx.compose.koinViewModel

internal fun NavGraphBuilder.onboardingDestinations(navController: NavHostController) {
    composable<Destination.Onboarding> {

        val viewModel: OnboardingStartViewModel = koinViewModel()
        InitNavigator(navController = navController, viewModel)

        OnboardingStartScreen(viewModel = viewModel)
    }
}
