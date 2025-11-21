package nl.q42.template.domain.main.di

import nl.q42.template.domain.main.usecase.FetchUserUseCase
import nl.q42.template.domain.main.usecase.GetUserFlowUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {

    singleOf(::FetchUserUseCase)
    singleOf(::GetUserFlowUseCase)
}