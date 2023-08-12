package com.bagus2x.toko.data

import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signIn(username: String, password: String)

    suspend fun signOut()

    fun authenticated(): Flow<Boolean>
}
