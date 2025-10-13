package nl.q42.template.home.di

import nl.q42.template.home.main.presentation.HomeViewModel
import nl.q42.template.home.second.presentation.HomeSecondViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {

    viewModelOf(::HomeViewModel)
    viewModelOf(::HomeSecondViewModel)
}