package com.bagus2x.toko.ui.utils

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
val Json = Json {
    explicitNulls = false
}
