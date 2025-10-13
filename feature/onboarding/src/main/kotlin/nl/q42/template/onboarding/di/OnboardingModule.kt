package nl.q42.template.onboarding.di

import nl.q42.template.onboarding.start.presentation.OnboardingStartViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val onboardingModule = module {

    viewModelOf(::OnboardingStartViewModel)
}