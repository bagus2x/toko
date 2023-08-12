package com.bagus2x.toko.data

import com.bagus2x.toko.model.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

val DummyStores = listOf(
    Store(
        storeId = "1121212",
        accountId = "lala",
        accountName = "baba",
        address = "Jakarta",
        areaId = "Jakarta",
        areaName = "Jakarta",
        channelId = "Jakarta",
        channelName = "",
        dcId = "ty",
        dcName = "ty",
        latitude = "${-6.241586}",
        longitude = "${106.992416}",
        regionId = "jkt",
        regionName = "Jakarta",
        storeCode = "J",
        storeName = "Market",
        subchannelId = "as",
        subchannelName = "as",
        distance = "1km",
        visitHistory = listOf()
    )
)

class FakeStoreRepository : StoreRepository {
    private val stores = MutableStateFlow(DummyStores)
    override suspend fun save(store: Store) {
        val exists = stores.value.find { it.storeId == store.storeId } != null
        if (exists) {
            stores.update { stores -> stores.map { if (it.storeId == store.storeId) store else it } }
            return
        }
        stores.update { it + listOf(store) }
    }

    override suspend fun save(stores: List<Store>) {
        this.stores.update { it + stores }
    }

    override fun getStore(storeId: String): Flow<Store?> {
        return stores.map { stores -> stores.find { it.storeId == storeId } }
    }

    override fun getStores(query: String): Flow<List<Store>> {
        return stores
    }
}
