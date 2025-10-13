package nl.q42.template.ui.di

import android.app.Application
import android.content.Context
import android.view.accessibility.AccessibilityManager
import kotlinx.coroutines.MainScope
import nl.q42.template.ui.compose.MainCoroutineScope
import nl.q42.template.ui.presentation.SnackbarPresenter
import nl.q42.template.ui.presentation.dialog.DialogPresenter
import nl.q42.template.ui.presentation.dialog.DialogPresenterImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val presentationModule = module {
    single<AccessibilityManager> {
        provideAccessibilityManager(get())
    }

    factory { MainCoroutineScope(MainScope()) }

    singleOf(::DialogPresenterImpl) { bind<DialogPresenter>() }
    singleOf(::SnackbarPresenter)
}

internal fun provideAccessibilityManager(applicationContext: Context): AccessibilityManager =
    applicationContext.getSystemService(
        Application.ACCESSIBILITY_SERVICE
    ) as AccessibilityManager