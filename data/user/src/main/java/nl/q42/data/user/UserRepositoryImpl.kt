package nl.q42.data.user

import android.util.Log
import nl.q42.data.user.local.UserLocalDataSource
import nl.q42.data.user.local.model.UserEntity
import nl.q42.data.user.local.model.mapToUser
import nl.q42.data.user.remote.UserRemoteDataSource
import nl.q42.domain.user.model.User
import nl.q42.domain.user.repo.UserRepository
import nl.q42.template.actionresult.domain.ActionResult
import nl.q42.template.actionresult.domain.getDataOrNull
import nl.q42.template.actionresult.domain.map
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : UserRepository {

    override suspend fun getUser(): ActionResult<User> {

        // get remotely
        val userEntityActionResult = userRemoteDataSource.getUser()
        // store locally
        userEntityActionResult.getDataOrNull()?.let { userEntity ->
            userLocalDataSource.setUser(userEntity)
        }

        // send response back to caller
        return userEntityActionResult.map(UserEntity::mapToUser)
    }
}
