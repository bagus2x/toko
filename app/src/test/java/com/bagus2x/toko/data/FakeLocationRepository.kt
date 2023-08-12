package com.bagus2x.toko.data

import com.bagus2x.toko.model.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class FakeLocationRepository : LocationRepository {
    override fun distance(location1: Location, location2: Location): Float {
        val r = 6371

        val dLat: Double = toRadians(location2.lat - location1.lat)
        val dLon: Double = toRadians(location2.lng - location1.lng)
        val location1Lat = toRadians(location1.lat)
        val location2Lat = toRadians(location2.lat)

        val a = sin(dLat / 2) * sin(dLat / 2) +
                sin(dLon / 2) * sin(dLon / 2) * cos(location1Lat) * cos(location2Lat)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return (r * c).toFloat() * 1000
    }

    private fun toRadians(deg: Double): Double {
        return deg * (Math.PI / 180)
    }

    override suspend fun getMyLocation(): Flow<Location?> {
        return flow {
            emit(
                Location(
                    lat = -6.241586,
                    lng = 106.992416,
                    display = "Bekasi"
                )
            )
        }
    }
}
