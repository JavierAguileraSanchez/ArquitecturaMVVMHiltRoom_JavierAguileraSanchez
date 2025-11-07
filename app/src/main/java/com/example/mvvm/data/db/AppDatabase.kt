package com.example.mvvm.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [IncidenciaEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun incidenciaDao(): IncidenciaDao
}
