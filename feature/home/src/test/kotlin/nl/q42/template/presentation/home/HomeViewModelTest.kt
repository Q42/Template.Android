package nl.q42.template.presentation.home

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import nl.q42.template.actionresult.domain.ActionResult
import nl.q42.template.domain.user.model.EmailAddress
import nl.q42.template.domain.user.model.User
import nl.q42.template.domain.user.usecase.GetUserUseCase
import nl.q42.template.ui.presentation.ViewStateString
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

class HomeViewModelTest() {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `WHEN I subscribe to uiState with a slow UserUseCase THEN I get the loading state and expected email address`() = runTest {
        val getUserUseCaseMock: GetUserUseCase = mockk()
        coEvery { getUserUseCaseMock.invoke() }.coAnswers {
            // demonstration of test scheduler. This does not actually block the test for 4 seconds
            delay(4.seconds)
            ActionResult.Success(User(EmailAddress("test@test.com")))
        }

        val viewModel = HomeViewModel(getUserUseCaseMock, mockk())

        viewModel.uiState.test {

            assertEquals(HomeViewState.Loading, awaitItem())
            val viewState = awaitItem()
            assertTrue(viewState is HomeViewState.Data)
            assertTrue((viewState as HomeViewState.Data).userEmailTitle is ViewStateString.Res)
        }
    }

    @Test
    fun `WHEN I subscribe to uiState with a fast UserUseCase THEN I get expected email address immediately`() = runTest {
        val getUserUseCaseMock: GetUserUseCase = mockk()
        coEvery { getUserUseCaseMock.invoke() }.returns(
            ActionResult.Success(
                User(EmailAddress("test@test.com"))
            )
        )

        val viewModel = HomeViewModel(getUserUseCaseMock, mockk())

        viewModel.uiState.test {
            val viewState = awaitItem()
            assertTrue(viewState is HomeViewState.Data)
            assertTrue((viewState as HomeViewState.Data).userEmailTitle is ViewStateString.Res)
        }
    }
}
