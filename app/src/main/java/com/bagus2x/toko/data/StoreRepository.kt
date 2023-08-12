package com.bagus2x.toko.data

import com.bagus2x.toko.model.Store
import kotlinx.coroutines.flow.Flow

interface StoreRepository {

    suspend fun save(store: Store)

    suspend fun save(stores: List<Store>)

    fun getStore(storeId: String): Flow<Store?>

    fun getStores(query: String): Flow<List<Store>>
}
