package nl.q42.template

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.aakira.napier.Napier
import nl.q42.template.core.utils.di.ConfigAppScheme
import nl.q42.template.navigation.Destination
import nl.q42.template.navigation.homeGraph
import nl.q42.template.navigation.onboardingDestinations
import nl.q42.template.ui.compose.composables.widgets.AppSurface
import nl.q42.template.ui.compose.composables.window.LocalSnackbarHostState
import nl.q42.template.ui.compose.composables.window.toSnackBarVisuals
import nl.q42.template.ui.presentation.SnackbarManager
import nl.q42.template.ui.theme.AppTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    @ConfigAppScheme
    lateinit var appDeepLinkScheme: String

    @Inject
    lateinit var snackbarManager: SnackbarManager

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge() // must be called before super.onCreate
        super.onCreate(savedInstanceState)

        Napier.d { "onCreate received, ${intent.data}" }


        setContent {

            val snackbarHostState = remember { SnackbarHostState() }
            SnackbarChangedEffect(snackbarHostState)

            CompositionLocalProvider(
                LocalSnackbarHostState provides snackbarHostState
            ) {
                AppTheme {

                    val navController = rememberNavController()

                    AppSurface(
                        modifier = Modifier.fillMaxSize(),
                    ) {

                        NavHost(
                            navController = navController,
                            startDestination = Destination.HomeGraph
                        ) {
                            homeGraph(
                                navController = navController,
                                appDeepLinkScheme = appDeepLinkScheme
                            )
                            onboardingDestinations(navController)
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        Napier.d { "onNewIntent received, ${intent.data}" }

    }

    /**
     * May set a Snackbar on the [snackbarHostState] if the [SnackbarManager] has a snackbar available.
     * To actually show the snackbar, snackbarHostState has to be used in a Scaffold, such as ScaffoldWithAppBar.
     */
    @Composable
    private fun SnackbarChangedEffect(snackbarHostState: SnackbarHostState) {
        val snackbarSpec by snackbarManager.uiState.collectAsStateWithLifecycle(
            initialValue = null
        )
        val snackbarVisuals = snackbarSpec?.toSnackBarVisuals()

        LaunchedEffect(snackbarVisuals) {
            snackbarHostState.currentSnackbarData?.dismiss()
            if (snackbarVisuals != null) {
                snackbarHostState.showSnackbar(snackbarVisuals)
            }
        }
    }
}
