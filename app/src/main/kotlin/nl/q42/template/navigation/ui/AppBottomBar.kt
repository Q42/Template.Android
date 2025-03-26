package nl.q42.template.navigation.ui

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import nl.q42.template.ui.compose.composables.text.BodyText

@SuppressLint("RestrictedApi")
@Composable
fun AppBottomBar(
    navController: NavHostController,
) {

    NavigationBar {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        BottomBarItem.entries.forEach { bottomBarItem: BottomBarItem ->

            val isSelected = currentDestination?.hierarchy?.any {
                it.hasRoute(bottomBarItem.destination::class)
            } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(bottomBarItem.destination) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // avoid multiple copies of the same destination when reselecting the same item
                        launchSingleTop = true
                        // restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(bottomBarItem.icon.resId),
                        contentDescription = stringResource(bottomBarItem.label)
                    )
                },
                label = { BodyText(stringResource(bottomBarItem.label)) },
            )
        }
    }
}
