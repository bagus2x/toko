package com.bagus2x.toko.ui.store

import com.bagus2x.toko.model.Location
import com.bagus2x.toko.model.Store
import java.io.File

data class StoreState(
    val myLocation: Location? = null,
    val store: Store? = null,
    val photo: File? = null,
    val message: String = "",
    val loading: Boolean = false,
    val visitSuccess: Boolean = false
)
