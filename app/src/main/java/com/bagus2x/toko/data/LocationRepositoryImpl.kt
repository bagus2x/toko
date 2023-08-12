package com.bagus2x.toko.data

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import com.bagus2x.toko.model.Location
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import timber.log.Timber

class LocationRepositoryImpl(
    private val context: Context
) : LocationRepository {
    private val geocoder = Geocoder(context)

    override fun distance(location1: Location, location2: Location): Float {
        val l1 = android.location.Location(null).apply {
            latitude = location1.lat
            longitude = location1.lng
        }
        val l2 = android.location.Location(null).apply {
            latitude = location2.lat
            longitude = location2.lng
        }
        return l1.distanceTo(l2)
    }

    override suspend fun getMyLocation(): Flow<Location?> {
        return callbackFlow {
            val gps = GpsMyLocationProvider(context)

            gps.startLocationProvider { location, _ ->
                Timber.i("HASIL $location")

                getDisplayName(
                    lat = location.latitude,
                    lng = location.longitude,
                    onResult = { name ->
                        trySend(
                            Location(
                                lat = location.latitude,
                                lng = location.longitude,
                                display = name
                            )
                        )
                    },
                    onError = {
                        trySend(
                            Location(
                                lat = location.latitude,
                                lng = location.longitude,
                                display = ""
                            )
                        )
                    }
                )
            }
            awaitClose {
                gps.stopLocationProvider()
            }
        }
    }

    private fun getDisplayName(
        lat: Double,
        lng: Double,
        onResult: (String) -> Unit = {},
        onError: (Exception) -> Unit = {}
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(
                lat,
                lng,
                1,
                object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: MutableList<Address>) {
                        val result = addresses.firstOrNull()
                            ?.getAddressLine(0)
                            ?: return onError(Exception("Location name not found"))
                        onResult(result)
                    }

                    override fun onError(errorMessage: String?) {
                        super.onError(errorMessage)
                        onError(RuntimeException(errorMessage))
                    }
                })
        } else {
            try {
                @Suppress("DEPRECATION")
                val addresses = geocoder
                    .getFromLocation(lat, lng, 1)
                if (addresses != null) {
                    val result = addresses.firstOrNull()
                        ?.getAddressLine(0)
                        ?: return onError(Exception("Location name not found"))
                    onResult(result)
                } else {
                    onError(RuntimeException("addresses not found"))
                }
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}
