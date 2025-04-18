package nl.q42.template.data.main

import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nl.q42.template.actionresult.domain.ActionResult
import nl.q42.template.actionresult.domain.map
import nl.q42.template.data.main.local.UserLocalDataSource
import nl.q42.template.data.main.local.model.mapToUser
import nl.q42.template.data.main.remote.UserRemoteDataSource
import nl.q42.template.domain.main.model.User
import nl.q42.template.domain.main.repo.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : UserRepository {

    override suspend fun fetchUser(): ActionResult<Unit> {

        // get remotely
        val userEntityActionResult = userRemoteDataSource.getUser()
        // store locally
        when (userEntityActionResult) {
            is ActionResult.Success -> {
                userLocalDataSource.setUser(userEntityActionResult.data)
            }

            is ActionResult.Error -> {
                Napier.e(userEntityActionResult.exception) { "fetchUser failed" }
            }
        }
        // we send back unit, the user needs to be observed
        return userEntityActionResult.map { }
    }

    override fun getUserFlow(): Flow<User?> =
        userLocalDataSource.getUserFlow().map { it?.mapToUser() }
}
