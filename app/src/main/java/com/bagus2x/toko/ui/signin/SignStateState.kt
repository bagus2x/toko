package com.bagus2x.toko.ui.signin

data class SignStateState(
    val username: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val authenticated: Boolean = false,
    val message: String = ""
)
