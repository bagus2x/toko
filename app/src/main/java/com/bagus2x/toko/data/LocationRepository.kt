package com.bagus2x.toko.data

import com.bagus2x.toko.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun distance(location1: Location, location2: Location): Float

    suspend fun getMyLocation(): Flow<Location?>
}
