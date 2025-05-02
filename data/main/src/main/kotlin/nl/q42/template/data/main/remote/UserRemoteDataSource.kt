package nl.q42.template.data.main.remote

import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.q42.template.actionresult.data.mapToActionResult
import nl.q42.template.actionresult.domain.ActionResult
import nl.q42.template.actionresult.domain.map
import nl.q42.template.data.main.local.model.UserEntity
import nl.q42.template.data.main.mapper.mapToEntity
import nl.q42.template.data.main.remote.model.UserDTO
import javax.inject.Inject

internal class UserRemoteDataSource @Inject constructor(
    private val userApi: UserApi
) {

    suspend fun getUser(): ActionResult<UserEntity> = withContext(Dispatchers.IO) {
        val apiActionResult = mapToActionResult {
            userApi.getUsers("test@test.com")
        }

        when (apiActionResult) {
            is ActionResult.Success -> {
                apiActionResult.map(UserDTO::mapToEntity)
            }

            is ActionResult.Error -> {
                Napier.e(apiActionResult.exception) { "getUser failed" }
                apiActionResult
            }
        }
    }
}
