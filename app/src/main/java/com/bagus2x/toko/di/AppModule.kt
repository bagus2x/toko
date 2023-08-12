package com.bagus2x.toko.di

import android.content.Context
import androidx.room.Room
import com.bagus2x.toko.data.AuthRepository
import com.bagus2x.toko.data.AuthRepositoryImpl
import com.bagus2x.toko.data.LocationRepository
import com.bagus2x.toko.data.LocationRepositoryImpl
import com.bagus2x.toko.data.StoreDao
import com.bagus2x.toko.data.StoreRepository
import com.bagus2x.toko.data.StoreRepositoryImpl
import com.bagus2x.toko.data.TokoDatabase
import com.bagus2x.toko.ui.utils.Json
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(
                    contentType = ContentType.Any,
                    json = Json
                )
            }
        }
    }

    @Provides
    @Singleton
    fun provideTokoDatabase(
        @ApplicationContext
        context: Context
    ): TokoDatabase {
        return Room
            .databaseBuilder(context, TokoDatabase::class.java, "toko.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideStoreDao(
        tokoDatabase: TokoDatabase
    ): StoreDao {
        return tokoDatabase.storeDao
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        storeDao: StoreDao,
        client: HttpClient
    ): AuthRepository {
        return AuthRepositoryImpl(storeDao, client)
    }

    @Provides
    @Singleton
    fun provideStoreRepository(
        storeDao: StoreDao
    ): StoreRepository {
        return StoreRepositoryImpl(storeDao)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(
        @ApplicationContext
        context: Context
    ): LocationRepository {
        return LocationRepositoryImpl(context)
    }
}
