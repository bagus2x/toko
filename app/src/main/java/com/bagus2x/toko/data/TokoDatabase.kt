package com.bagus2x.toko.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.bagus2x.toko.model.Store
import com.bagus2x.toko.model.VisitHistory
import com.bagus2x.toko.ui.utils.Json
import kotlinx.serialization.encodeToString

@Database(
    version = 1,
    entities = [Store::class],
    exportSchema = true,
)
@TypeConverters(TokoDatabase.Converters::class)
abstract class TokoDatabase : RoomDatabase() {
    abstract val storeDao: StoreDao

    class Converters {

        @TypeConverter
        fun visitHistoriesToString(value: List<VisitHistory>): String {
            return Json.encodeToString(value)
        }

        @TypeConverter
        fun stringToVisitHistories(value: String): List<VisitHistory> {
            return Json.decodeFromString(value)
        }
    }
}
