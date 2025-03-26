package nl.q42.template.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

internal fun NavGraphBuilder.profileGraph(navController: NavHostController) {
    navigation<Destination.ProfileGraph>(startDestination = Destination.Profile) {

        composable<Destination.Profile> {
            // put your Profile screen here
            Box(modifier = Modifier
                .size(100.dp)
                .background(Color.Red))
        }
    }
}
