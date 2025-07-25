package nl.q42.template.ui.di

import android.app.Application
import android.view.accessibility.AccessibilityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.q42.template.ui.presentation.dialog.DialogPresenter
import nl.q42.template.ui.presentation.dialog.DialogPresenterImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class PresentationModule {

    @Singleton
    @Provides
    fun provideAccessibilityManager(application: Application): AccessibilityManager =
        application.getSystemService(
            Application.ACCESSIBILITY_SERVICE
        ) as AccessibilityManager

    @Provides
    fun providesDialogPresenter(dialogPresenter: DialogPresenterImpl): DialogPresenter =
        dialogPresenter

}
