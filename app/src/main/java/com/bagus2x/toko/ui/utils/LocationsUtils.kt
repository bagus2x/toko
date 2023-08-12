package com.bagus2x.toko.ui.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Geocoder.GeocodeListener
import android.location.Location
import android.os.Build
import kotlinx.coroutines.suspendCancellableCoroutine
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object LocationsUtils {

    suspend fun getMyLocation(context: Context): Location = suspendCancellableCoroutine { cont ->
        val myLocationProvider = GpsMyLocationProvider(context)

        myLocationProvider.startLocationProvider { location, _ ->
            if (cont.isActive) {
                cont.resume(location)
            }
        }

        cont.invokeOnCancellation {
            myLocationProvider.stopLocationProvider()
        }
    }

    suspend fun getDisplayName(context: Context, location: Location): String? {
        return kotlin
            .runCatching {
                getAddresses(context, location, 1)
                    .firstOrNull()
                    ?.getAddressLine(0)
            }
            .getOrNull()
    }

    suspend fun getAddresses(context: Context, location: Location, max: Int): List<Address> =
        suspendCancellableCoroutine { cont ->
            val geocoder = Geocoder(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    max,
                    object : GeocodeListener {
                        override fun onGeocode(addresses: MutableList<Address>) {
                            cont.resume(addresses)
                        }

                        override fun onError(errorMessage: String?) {
                            super.onError(errorMessage)
                            cont.resumeWithException(RuntimeException(errorMessage))
                        }
                    })
            } else {
                try {
                    @Suppress("DEPRECATION")
                    val addresses = geocoder
                        .getFromLocation(location.latitude, location.longitude, max)
                    if (addresses != null) {
                        cont.resume(addresses)
                    } else {
                        cont.resumeWithException(RuntimeException("addresses not found"))
                    }
                } catch (e: Exception) {
                    cont.resumeWithException(e)
                }
            }
        }
}
