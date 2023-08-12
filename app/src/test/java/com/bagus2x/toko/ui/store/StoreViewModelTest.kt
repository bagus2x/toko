package com.bagus2x.toko.ui.store

import androidx.lifecycle.SavedStateHandle
import com.bagus2x.toko.data.FakeLocationRepository
import com.bagus2x.toko.data.FakeStoreRepository
import com.bagus2x.toko.model.Location
import com.bagus2x.toko.utils.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File

class StoreViewModelTest {
    private lateinit var viewModel: StoreViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        viewModel = StoreViewModel(
            savedStateHandle = SavedStateHandle(mapOf("storeId" to "1121212")),
            storeRepository = FakeStoreRepository(),
            locationRepository = FakeLocationRepository()
        )
    }

    @Test
    fun `visit should error if photo is null`() = runTest {
        viewModel.visit()

        Assert.assertEquals("Take a photo first", viewModel.state.first().message)
    }

    @Test
    fun `visit should error if location is null`() = runTest {
        viewModel.setPhoto(File(""))
        viewModel.visit()

        Assert.assertNotNull(viewModel.state.first().photo)
        Assert.assertEquals("Tag location first", viewModel.state.first().message)
    }

    @Test
    fun `visit should error if distance is greater than 100m`() = runTest {
        viewModel.setPhoto(File(""))
        viewModel.setMyLocation(Location(0.0, 0.0, ""))
        viewModel.visit()

        Assert.assertNotNull(viewModel.state.first().photo)
        Assert.assertNotNull(viewModel.state.first().myLocation)
        Assert.assertEquals(
            "You must be less than 100 meters away from the store",
            viewModel.state.first().message
        )
    }

    @Test
    fun `visit should success and not error`() = runTest {
        viewModel.setPhoto(File(""))
        viewModel.setMyLocation(
            Location(
                lat = -6.241586,
                lng = 106.992416,
                display = "Bekasi"
            )
        )
        viewModel.visit()

        Assert.assertNotNull(viewModel.state.first().photo)
        Assert.assertNotNull(viewModel.state.first().myLocation)
        Assert.assertTrue(viewModel.state.first().visitSuccess)
        Assert.assertEquals("Store visited", viewModel.state.first().message)
    }
}
