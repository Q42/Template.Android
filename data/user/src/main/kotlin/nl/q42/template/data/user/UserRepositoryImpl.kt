package nl.q42.template.data.user

import javax.inject.Inject
import nl.q42.template.actionresult.domain.ActionResult
import nl.q42.template.actionresult.domain.getDataOrNull
import nl.q42.template.actionresult.domain.map
import nl.q42.template.data.user.local.UserLocalDataSource
import nl.q42.template.data.user.local.model.UserEntity
import nl.q42.template.data.user.local.model.mapToUser
import nl.q42.template.data.user.remote.UserRemoteDataSource
import nl.q42.template.domain.user.model.User
import nl.q42.template.domain.user.repo.UserRepository

internal class UserRepositoryImpl
@Inject
constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {
    override suspend fun getUser(): ActionResult<User> {
        // get remotely
        val userEntityActionResult = userRemoteDataSource.getUser()
        // store locally
        userEntityActionResult.getDataOrNull()?.let { userEntity ->
            userLocalDataSource.setUser(userEntity)
        }

        // send response back to caller (note that it's often better to expose a Flow instead).
        return userEntityActionResult.map(UserEntity::mapToUser)
    }
}
