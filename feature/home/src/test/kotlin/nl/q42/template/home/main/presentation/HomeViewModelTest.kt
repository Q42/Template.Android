package nl.q42.template.home.main.presentation

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import nl.q42.template.actionresult.domain.ActionResult
import nl.q42.template.domain.user.usecase.FetchUserUseCase
import nl.q42.template.domain.user.usecase.GetUserFlowUseCase
import nl.q42.template.presentation.home.MainDispatcherRule
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

/**
 * An example ViewModel test. Weather you want to test ViewModels is up to you. On most projects,
 * you probably want to focus on unit testing the Domain layer first.
 */
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `WHEN fetchUserUseCase is called THEN loading state is set on the ViewState`() = runTest {

        val fetchUserUseCaseMock: FetchUserUseCase = mockk()
        coEvery { fetchUserUseCaseMock() }.coAnswers {
            delay(1.seconds)
            ActionResult.Success(Unit)
        }

        val getUserFlowUseCaseMock: GetUserFlowUseCase = mockk()
        coEvery { getUserFlowUseCaseMock() } returns flowOf()

        val viewModel = HomeViewModel(
            fetchUserUseCase = fetchUserUseCaseMock,
            getUserFlowUseCase = getUserFlowUseCaseMock,
            navigator = mockk()
        )

        viewModel.uiState.test {
            assertTrue(awaitItem().isLoading)
        }
    }

    @Test
    fun `WHEN fetchUserUseCase returns an error THEN an error is show on the ViewState`() = runTest {

        val errorResult = ActionResult.Error.Other(Exception()) as ActionResult<Unit>

        val fetchUserUseCaseMock: FetchUserUseCase = mockk()
        coEvery { fetchUserUseCaseMock() } returns errorResult

        val getUserFlowUseCaseMock: GetUserFlowUseCase = mockk()
        coEvery { getUserFlowUseCaseMock() } returns flowOf()

        val viewModel = HomeViewModel(
            fetchUserUseCase = fetchUserUseCaseMock,
            getUserFlowUseCase = getUserFlowUseCaseMock,
            navigator = mockk()
        )

        viewModel.uiState.test {
            assertTrue(awaitItem().showError)
        }
    }
}
