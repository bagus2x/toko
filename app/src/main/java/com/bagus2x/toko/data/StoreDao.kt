package com.bagus2x.toko.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bagus2x.toko.model.Store
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(store: Store)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(stores: List<Store>)

    @Query("SELECT * FROM store WHERE store_id = :storeId")
    fun getStore(storeId: String): Flow<Store?>

    @Query("SELECT * FROM store WHERE store_name LIKE '%' || :query || '%'")
    fun getStores(query: String): Flow<List<Store>>

    @Query("SELECT COUNT(*) FROM store")
    fun countStore(): Flow<Int>

    @Query("DELETE FROM store")
    suspend fun deleteAll()
}
