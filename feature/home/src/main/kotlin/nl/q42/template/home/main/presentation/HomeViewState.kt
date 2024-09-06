package nl.q42.template.home.main.presentation

import nl.q42.template.ui.presentation.ViewStateString

data class HomeViewState(
    val userEmailTitle: ViewStateString? = null,
    val isLoading: Boolean = false,
    val showError: Boolean = false,
)
