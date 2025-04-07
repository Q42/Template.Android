package nl.q42.template

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.aakira.napier.Napier
import nl.q42.template.navigation.Destination
import nl.q42.template.navigation.homeGraph
import nl.q42.template.navigation.onboardingDestinations
import nl.q42.template.ui.compose.composables.widgets.TemplateSurface
import nl.q42.template.ui.theme.TemplateTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Napier.d { "onCreate received, ${intent.data}" }

        enableEdgeToEdge()

        setContent {

            TemplateTheme {

                val navController = rememberNavController()

                TemplateSurface(
                    modifier = Modifier.fillMaxSize(),
                ) {

                    NavHost(navController = navController, startDestination = Destination.HomeGraph) {
                        homeGraph(navController)
                        onboardingDestinations(navController)
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        Napier.d { "onNewIntent received, ${intent.data}" }

    }
}
