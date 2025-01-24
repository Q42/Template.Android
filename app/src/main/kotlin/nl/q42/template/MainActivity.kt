package nl.q42.template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.q42.template.navigation.AppNavigation
import nl.q42.template.navigation.NavGraphs
import nl.q42.template.ui.compose.composables.widgets.TemplateSurface
import nl.q42.template.ui.theme.TemplateTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            TemplateTheme {

                val navController = rememberNavController()

                TemplateSurface (
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AppNavigation(navController = navController, navGraph = NavGraphs.root)
                }
            }
        }
    }
}
