package nl.q42.template.home.second.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.q42.template.navigation.Destination
import nl.q42.template.navigation.viewmodel.RouteNavigator
import org.koin.core.annotation.Provided

class HomeSecondViewModel(
    private val navigator: RouteNavigator,
    @Provided params: Destination.HomeSecond,
) : ViewModel(), RouteNavigator by navigator {


    private val _uiState = MutableStateFlow(HomeSecondViewState(params.title))
    val uiState: StateFlow<HomeSecondViewState> = _uiState.asStateFlow()

    fun onBackClicked() {
        navigateUp()
    }
}
