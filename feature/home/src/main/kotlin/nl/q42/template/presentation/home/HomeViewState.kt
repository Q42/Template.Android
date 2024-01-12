package nl.q42.template.presentation.home

import nl.q42.template.ui.presentation.ViewStateString

sealed class HomeViewState {
    data class Data(val userEmailTitle: ViewStateString? = null) : HomeViewState()

    object Loading : HomeViewState()

    object Error : HomeViewState()

    object Empty : HomeViewState()
}
