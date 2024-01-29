package nl.q42.template.domain.user.usecase

import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.q42.template.actionresult.domain.ActionResult
import nl.q42.template.domain.user.model.User
import nl.q42.template.domain.user.repo.UserRepository

// A UseCase models an action so the name should begin with a verb. For Flows, use: GetSomethingFlowUseCase
class GetUserUseCase
@Inject
constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(): ActionResult<User> = withContext(Dispatchers.Default) {
        userRepository.getUser()
    }
}
