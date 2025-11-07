package com.example.mvvm.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface IncidenciaDao {
    @Query("SELECT * FROM incidencias ORDER BY fechaCreacion DESC")
    fun observeAll(): Flow<List<IncidenciaEntity>>

    @Query("SELECT * FROM incidencias WHERE id = :id")
    fun observeById(id: Long): Flow<IncidenciaEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(incidencia: IncidenciaEntity): Long

    @Update
    suspend fun update(incidencia: IncidenciaEntity)

    @Delete
    suspend fun delete(incidencia: IncidenciaEntity)

    @Query("DELETE FROM incidencias WHERE id = :id")
    suspend fun deleteById(id: Long)
}
