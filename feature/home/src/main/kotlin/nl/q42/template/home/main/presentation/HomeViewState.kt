package nl.q42.template.home.main.presentation

import nl.q42.template.ui.presentation.ViewStateString

sealed interface HomeViewState {
    data class Content(val userEmailTitle: ViewStateString) : HomeViewState
    data object Loading : HomeViewState
    data object Error : HomeViewState
}
