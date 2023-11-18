package com.kaleksandra.coredata.network.di

import android.content.Context
import com.kaleksandra.coredata.network.database.DataStoreProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): DataStoreProvider = DataStoreProvider(context, dispatcher)
}