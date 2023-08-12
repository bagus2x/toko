package com.bagus2x.toko.model

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val message: String,
    val status: String,
    val stores: List<Store> = emptyList()
)
