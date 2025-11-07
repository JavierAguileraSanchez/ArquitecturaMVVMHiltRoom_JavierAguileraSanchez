package com.example.mvvm.data

import com.example.mvvm.data.db.IncidenciaDao
import com.example.mvvm.data.db.IncidenciaEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IncidenciasRepository @Inject constructor(
    private val dao: IncidenciaDao
) {
    fun observeAll(): Flow<List<IncidenciaEntity>> = dao.observeAll()
    fun observeById(id: Long): Flow<IncidenciaEntity?> = dao.observeById(id)

    suspend fun add(incidencia: IncidenciaEntity) = dao.upsert(incidencia)
    suspend fun update(incidencia: IncidenciaEntity) = dao.update(incidencia)
    suspend fun deleteById(id: Long) = dao.deleteById(id)
}
