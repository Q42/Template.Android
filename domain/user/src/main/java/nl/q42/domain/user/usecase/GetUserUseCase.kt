package nl.q42.domain.user.usecase

import nl.q42.data.UserRepository
import nl.q42.data.user.models.User
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(): User {
        return userRepository.getUser()
    }
}
