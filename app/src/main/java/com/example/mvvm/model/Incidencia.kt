package com.example.mvvm.model

data class Incidencia(
    val id: String = "",
    val nombre: String,
    val prioridad: String,
    val urgencia: Int,
    val activo: String,
    val estado: String,
    val responsable: String,
    val fechaCreacion: Long,
    val descripcion: String,
    val photoUrl: String
)
