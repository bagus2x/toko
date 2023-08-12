package com.bagus2x.toko.ui.storelist

import com.bagus2x.toko.model.Location
import com.bagus2x.toko.model.Store

data class StoreListState(
    val stores: List<Store> = emptyList(),
    val query: String = "",
    val myLocation: Location? = null,
    val gpsEnabled: Boolean = false,
    val message: String = ""
)
