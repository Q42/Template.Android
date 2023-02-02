package nl.q42.presentation.home

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import nl.q42.presentation.home.presentation.HomeViewState

@Composable
internal fun HomeContent(
    viewState: HomeViewState,
    onLoadClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally,
    ) {

        /**
         * This is dummy. Use the strings file IRL.
         */
        when (viewState) {
            is HomeViewState.Data -> Text(text = "Email: ${viewState.userEmail}")
            HomeViewState.Empty -> {}
            HomeViewState.Error -> Text(text = "Error")
            HomeViewState.Loading -> Text(text = "Loading")
        }

        Button(onClick = onLoadClicked) {
            Text("Refresh")
        }
    }
}

@Preview
@Composable
private fun HomeContentErrorPreview() {
    HomeContent(HomeViewState.Error, {})
}

@Preview
@Composable
private fun HomeContentLoadingPreview() {
    HomeContent(HomeViewState.Loading, {})
}

@Preview
@Composable
private fun HomeContentEmptyPreview() {
    HomeContent(HomeViewState.Empty, {})
}

@Preview
@Composable
private fun HomeContentDataPreview() {
    HomeContent(HomeViewState.Data("preview@preview.com"), {})
}