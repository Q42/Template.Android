package nl.q42.data.user

import nl.q42.data.user.local.UserLocalDataSource
import nl.q42.data.user.local.model.mapToUser
import nl.q42.data.user.remote.UserRemoteDataSource
import nl.q42.domain.user.model.User
import nl.q42.domain.user.repo.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : UserRepository {

    override fun getUser(): User {
        val userEntity = userRemoteDataSource.getUser()
        userLocalDataSource.setUser(userEntity)
        return userEntity.mapToUser() // TODO or watch a flow
    }
}
