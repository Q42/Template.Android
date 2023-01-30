package nl.q42.domain.user.usecase

import nl.q42.domain.user.UserRepository
import nl.q42.domain.user.model.User

class GetUserUseCase(private val userRepository: UserRepository) {

    operator fun invoke(): User {
        return userRepository.getUser()
    }
}