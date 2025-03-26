package nl.q42.template

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.q42.template.navigation.Destination
import nl.q42.template.navigation.fullscreenDestinations
import nl.q42.template.navigation.homeGraph
import nl.q42.template.navigation.onboardingDestinations
import nl.q42.template.navigation.profileGraph
import nl.q42.template.navigation.ui.AppBottomBar
import nl.q42.template.ui.animation.SlidedVisibility
import nl.q42.template.ui.compose.composables.widgets.TemplateSurface
import nl.q42.template.ui.theme.TemplateTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            TemplateTheme {

                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val showBottomBar by remember(navBackStackEntry) {
                    mutableStateOf(fullscreenDestinations.none { navBackStackEntry?.destination?.hasRoute(it) == true })
                }

                Scaffold(bottomBar = {
                    SlidedVisibility(showBottomBar) { AppBottomBar(navController = navController) }
                }) {

                    TemplateSurface(
                        modifier = Modifier.fillMaxSize(),
                    ) {

                        NavHost(navController = navController, startDestination = Destination.HomeGraph) {
                            homeGraph(navController)
                            profileGraph(navController)
                            onboardingDestinations(navController)
                        }
                    }
                }
            }
        }
    }
}
