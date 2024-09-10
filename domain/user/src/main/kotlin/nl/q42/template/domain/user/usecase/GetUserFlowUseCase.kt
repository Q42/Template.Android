package nl.q42.template.domain.user.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import nl.q42.template.domain.user.model.User
import nl.q42.template.domain.user.repo.UserRepository
import javax.inject.Inject

class GetUserFlowUseCase @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(): Flow<User?> =
        userRepository
            .getUserFlow()
            .flowOn(Dispatchers.Default)
}
