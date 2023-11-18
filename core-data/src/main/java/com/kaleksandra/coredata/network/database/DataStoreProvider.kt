package com.kaleksandra.coredata.network.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.kaleksandra.coredata.network.Completable
import com.kaleksandra.coredata.network.Effect
import com.kaleksandra.coredata.network.di.IoDispatcher
import com.kaleksandra.coredata.network.tryCatch
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val DATA_STORE_NAME = "data_store"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class DataStoreProvider @Inject constructor(
    @ApplicationContext val context: Context,
    @IoDispatcher val dispatcher: CoroutineDispatcher,
) {
    suspend fun <T> update(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { dataStore -> dataStore[key] = value }
    }

    fun <T> get(key: Preferences.Key<T>): Flow<T?> {
        return context.dataStore.data
            .catchException()
            .map { preferences -> preferences[key] }
    }

    fun <T> contains(key: Preferences.Key<T>): Flow<Boolean> {
        return context.dataStore.data
            .catchException()
            .map { preference -> preference.contains(key) }
    }

    suspend fun <T> remove(key: Preferences.Key<T>): Effect<Completable> {
        return tryCatch(dispatcher) { context.dataStore.edit { it.remove(key) } }
    }

    private fun Flow<Preferences>.catchException(): Flow<Preferences> =
        this.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
}