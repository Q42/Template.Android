package nl.q42.domain.user.usecase

import nl.q42.domain.user.model.User
import nl.q42.domain.user.repo.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(): User {
        return userRepository.getUser()
    }
}
