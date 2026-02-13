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
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import co.touchlab.kermit.Logger
import nl.q42.template.core.utils.config.AppScheme
import nl.q42.template.navigation.Destination
import nl.q42.template.navigation.deeplink.DeeplinkParser
import nl.q42.template.navigation.homeEntry
import nl.q42.template.navigation.onboardingEntry
import nl.q42.template.navigation.viewmodel.Navigator
import nl.q42.template.navigation.viewmodel.rememberNavigationState
import nl.q42.template.navigation.viewmodel.toEntries
import nl.q42.template.ui.compose.composables.widgets.AppSurface
import nl.q42.template.ui.compose.composables.window.LocalSnackbarHostState
import nl.q42.template.ui.compose.composables.window.toSnackBarVisuals
import nl.q42.template.ui.presentation.SnackbarPresenter
import nl.q42.template.ui.theme.AppTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val appDeepLinkScheme: AppScheme by inject()

    private val snackbarPresenter: SnackbarPresenter by inject()

    private val deeplinkParser: DeeplinkParser by inject()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge() // must be called before super.onCreate
        super.onCreate(savedInstanceState)

        Logger.d { "onCreate received, ${intent.data}" }

        val startDestination: Destination = deeplinkParser.parseIntent(intent) ?: Destination.Home
        Logger.i { "Start destination: $startDestination" }

        setContent {

            val navigationState = rememberNavigationState(
                startRoute = startDestination,
                topLevelRoutes = setOf<NavKey>(
                    // the destinations that can be used to enter the app
                    Destination.Home,
                    Destination.Onboarding
                )
            )

            val navigator = remember { Navigator(navigationState) }
            val entryProvider: (NavKey) -> NavEntry<NavKey> = entryProvider {
                homeEntry(navigator = navigator)
                onboardingEntry(navigator = navigator)
            }



            val snackbarHostState = remember { SnackbarHostState() }
            SnackbarChangedEffect(snackbarHostState)

            CompositionLocalProvider(
                LocalSnackbarHostState provides snackbarHostState
            ) {
                AppTheme {

                    AppSurface(
                        modifier = Modifier.fillMaxSize(),
                    ) {

                        NavDisplay(
                            entries = navigationState.toEntries(entryProvider),
                            onBack = { navigator.goBack() },
                            sceneStrategy = remember { DialogSceneStrategy() }
                        )

                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        Logger.d { "onNewIntent received, ${intent.data}" }

    }

    /**
     * May set a Snackbar on the [snackbarHostState] if the [SnackbarPresenter] has a snackbar available.
     * To actually show the snackbar, snackbarHostState has to be used in a Scaffold, such as ScaffoldWithAppBar.
     */
    @Composable
    private fun SnackbarChangedEffect(snackbarHostState: SnackbarHostState) {
        val snackbarSpec by snackbarPresenter.uiState.collectAsStateWithLifecycle(
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
