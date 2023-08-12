package com.bagus2x.toko.data

import com.bagus2x.toko.BuildConfig
import com.bagus2x.toko.model.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber

class AuthRepositoryImpl(
    private val storeDao: StoreDao,
    private val client: HttpClient,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AuthRepository {

    override suspend fun signIn(username: String, password: String) = withContext(dispatcher) {
        val multiplatform = MultiPartFormDataContent(formData {
            append("username", username)
            append("password", password)
        })

        val result = client
            .post("$HTTP_BASE_URL/api/sariroti_md/index.php/login/loginTest") {
                setBody(multiplatform)
            }.body<Result>()

        if (result.status == "success") {
            storeDao.save(result.stores)
        } else {
            Timber.e(result.message)
            throw Exception(result.message)
        }
    }

    override fun authenticated(): Flow<Boolean> {
        // mock token
        return storeDao.countStore().map { it != 0 }.flowOn(dispatcher)
    }

    override suspend fun signOut() = withContext(dispatcher) {
        storeDao.deleteAll()
    }

    companion object {
        private const val HTTP_BASE_URL = BuildConfig.HTTP_BASE_URL
    }
}
