package com.example.mvvm.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "incidencias")
data class IncidenciaEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val prioridad: String,   // Baja, Media, Alta
    val urgencia: Int,       // 1,2,3
    val activo: String,      // PC1, PC2, etc.
    val estado: String,      // Abierta, Resuelta, Rechazada
    val responsable: String,
    val fechaCreacion: Long = System.currentTimeMillis(),
    val descripcion: String,
    val photoUrl: String = ""
)
