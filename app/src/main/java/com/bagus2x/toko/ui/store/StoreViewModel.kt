package com.bagus2x.toko.ui.store

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagus2x.toko.data.LocationRepository
import com.bagus2x.toko.data.StoreRepository
import com.bagus2x.toko.model.Location
import com.bagus2x.toko.model.Store
import com.bagus2x.toko.model.VisitHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDateTime
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class StoreViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val storeRepository: StoreRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {
    private val _state = MutableStateFlow(StoreState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            savedStateHandle.getStateFlow<String?>("storeId", null)
                .filterNotNull()
                .flatMapLatest { storeRepository.getStore(it) }
                .collectLatest { store ->
                    _state.update { it.copy(store = store) }
                }
        }
    }

    fun setMyLocation(myLocation: Location?) {
        viewModelScope.launch {
            if (myLocation == null) {
                _state.update { it.copy(loading = true) }
                val location = locationRepository.getMyLocation().filterNotNull().first()
                _state.update { it.copy(myLocation = location, loading = false) }
            } else {
                _state.update { it.copy(myLocation = myLocation) }
            }
        }
    }

    fun resetLocation() {
        _state.update { it.copy(myLocation = null) }
    }

    fun setPhoto(file: File?) {
        _state.update { it.copy(photo = file) }
    }

    fun visit() {
        val photo = state.value.photo
        val myLocation = state.value.myLocation
        val store = state.value.store ?: return
        val storeLocation = store.location

        if (photo == null) {
            _state.update { it.copy(message = "Take a photo first") }
            return
        }
        if (myLocation == null) {
            _state.update { it.copy(message = "Tag location first") }
            return
        }
        if (storeLocation == null) {
            _state.update { it.copy(message = "Store location not found") }
            return
        }
        if (locationRepository.distance(myLocation, storeLocation) > 100) {
            _state.update { it.copy(message = "You must be less than 100 meters away from the store") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(loading = true) }

            try {
                val history = VisitHistory(
                    location = myLocation,
                    photo = photo,
                    visitedAt = LocalDateTime.now()
                )
                val updatedStore = store.copy(
                    visitHistory = store.visitHistory
                        .toMutableList()
                        .apply { add(history) }
                )

                storeRepository.save(updatedStore)
                _state.update {
                    it.copy(
                        loading = false,
                        visitSuccess = true,
                        message = "Store visited"
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(loading = false, message = e.message ?: "") }
            }
        }
    }

    fun consumeMessage() {
        _state.update { it.copy(message = "") }
    }
}

private val Store.location: Location?
    get() {
        val lat = latitude?.toDoubleOrNull() ?: return null
        val lng = longitude?.toDoubleOrNull() ?: return null
        return Location(lat, lng, "")
    }
