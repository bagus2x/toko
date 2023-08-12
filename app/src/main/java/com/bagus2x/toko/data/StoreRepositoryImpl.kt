package com.bagus2x.toko.data

import com.bagus2x.toko.model.Store
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class StoreRepositoryImpl(
    private val storeDao: StoreDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : StoreRepository {

    override suspend fun save(store: Store) = withContext(dispatcher) {
        storeDao.save(store)
    }

    override suspend fun save(stores: List<Store>) = withContext(dispatcher) {
        storeDao.save(stores)
    }

    override fun getStore(storeId: String): Flow<Store?> {
        return storeDao.getStore(storeId).flowOn(dispatcher)
    }

    override fun getStores(query: String): Flow<List<Store>> {
        return storeDao.getStores(query).flowOn(dispatcher)
    }
}
