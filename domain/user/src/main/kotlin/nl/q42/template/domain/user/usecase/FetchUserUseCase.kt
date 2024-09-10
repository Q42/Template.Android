package nl.q42.template.domain.user.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.q42.template.actionresult.domain.ActionResult
import nl.q42.template.domain.user.repo.UserRepository
import javax.inject.Inject

// A UseCase models an action so the name should begin with a verb. For Flows, use: GetSomethingFlowUseCase
class FetchUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(): ActionResult<Unit> = withContext(Dispatchers.Default) {
        userRepository.fetchUser()
    }
}
