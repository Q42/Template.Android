package nl.q42.template.data.user.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.q42.template.actionresult.data.mapToActionResult
import nl.q42.template.actionresult.domain.ActionResult
import nl.q42.template.actionresult.domain.map
import nl.q42.template.data.user.local.model.UserEntity
import nl.q42.template.data.user.mapper.mapToEntity
import nl.q42.template.data.user.remote.model.UserDTO
import javax.inject.Inject

internal class UserRemoteDataSource @Inject constructor(
    private val userApi: UserApi
) {

    suspend fun getUser(): ActionResult<UserEntity> = withContext(Dispatchers.IO) {
        val apiActionResult = mapToActionResult {
            userApi.getUsers("test@test.com")
        }
        apiActionResult.map(UserDTO::mapToEntity)
    }
}
