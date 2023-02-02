package nl.q42.template.domain.user.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.q42.template.domain.user.model.User
import nl.q42.template.domain.user.repo.UserRepository
import nl.q42.template.actionresult.domain.ActionResult
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(): ActionResult<User> = withContext(Dispatchers.Default) {
        userRepository.getUser()
    }
}
