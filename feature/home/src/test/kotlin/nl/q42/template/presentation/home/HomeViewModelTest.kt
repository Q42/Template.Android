package nl.q42.template.presentation.home

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import nl.q42.template.actionresult.domain.ActionResult
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
            ActionResult.Success(User("test@test.com"))
        }

        val viewModel = HomeViewModel(getUserUseCaseMock, mockk())

        viewModel.uiState.test {
            val expectedData: HomeViewState = HomeViewState.Data(ViewStateString.Basic("test@test.com"))

            assertEquals(HomeViewState.Loading, awaitItem())
            assertEquals(expectedData, awaitItem())
        }
    }

    @Test
    fun `WHEN I subscribe to uiState with a fast UserUseCase THEN I get expected email address immediately`() = runTest {
        val getUserUseCaseMock: GetUserUseCase = mockk()
        coEvery { getUserUseCaseMock.invoke() }.returns(
            ActionResult.Success(
                User("test@test.com")
            )
        )

        val viewModel = HomeViewModel(getUserUseCaseMock, mockk())

        viewModel.uiState.test {
            val expectedData: HomeViewState = HomeViewState.Data(ViewStateString.Basic("test@test.com"))
            assertEquals(expectedData, awaitItem())
        }
    }
}
