package com.example.mvvm.di

import android.content.Context
import androidx.room.Room
import com.example.mvvm.data.IncidenciasRepository
import com.example.mvvm.data.db.AppDatabase
import com.example.mvvm.data.db.IncidenciaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "incidencias.db").build()

    @Singleton
    @Provides
    fun provideIncidenciaDao(db: AppDatabase): IncidenciaDao = db.incidenciaDao()

    @Singleton
    @Provides
    fun provideIncidenciasRepository(dao: IncidenciaDao): IncidenciasRepository =
        IncidenciasRepository(dao)
}
