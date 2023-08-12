package com.bagus2x.toko.ui.storelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagus2x.toko.data.LocationRepository
import com.bagus2x.toko.data.StoreRepository
import com.bagus2x.toko.model.Location
import com.bagus2x.toko.model.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class StoreListViewModel @Inject constructor(
    storeRepository: StoreRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {
    private val _state = MutableStateFlow(StoreListState())
    val state = _state.asStateFlow()

    init {
        // Get all stores
        viewModelScope.launch {
            _state
                .map { it.query }
                .distinctUntilChanged()
                .flatMapLatest { storeRepository.getStores(it) }
                .collectLatest { stores ->
                    _state.update { it.copy(stores = stores) }
                }
        }

        viewModelScope.launch {
            _state
                .filter { it.gpsEnabled }
                .flatMapLatest { locationRepository.getMyLocation() }
                .filterNotNull()
                .collectLatest { myLocation ->
                    _state.update {
                        it.copy(
                            myLocation = myLocation,
                            stores = it.stores.map { store -> updateDistance(store, myLocation) }
                        )
                    }
                }
        }
    }

    private fun updateDistance(store: Store, myLocation: Location): Store {
        val storeLat = store.latitude?.toDoubleOrNull()
        val storeLng = store.longitude?.toDoubleOrNull()
        return if (storeLat == null || storeLng == null) {
            store
        } else {
            val distance = locationRepository.distance(myLocation, Location(storeLat, storeLng, ""))
            store.copy(distance = formatDistance(distance))
        }
    }

    fun setQuery(query: String) {
        _state.update { it.copy(query = query) }
    }

    fun setGpsEnabled() {
        _state.update { it.copy(gpsEnabled = true) }
    }
}

private val decimalFormat = DecimalFormat("#,###.##")

private fun formatDistance(distanceInMeters: Float): String {
    return if (distanceInMeters >= 1000) {
        "${decimalFormat.format(distanceInMeters / 1000)}km"
    } else {
        "${decimalFormat.format(distanceInMeters)}m"
    }
}
