package com.example.mvvm.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvvm.data.db.IncidenciaEntity
import com.example.mvvm.viewmodel.IncidenciasViewModel

@Composable
fun RegistroIncidenciaScreen(
    vm: IncidenciasViewModel = hiltViewModel(),
    incidenciaId: Long? = null,
    onDone: () -> Unit
) {
    val ui by vm.ui.collectAsState()
    val existing = remember(ui.incidencias, incidenciaId) {
        ui.incidencias.firstOrNull { it.id == incidenciaId }
    }
    val isEdit = existing != null

    var nombre by rememberSaveable { mutableStateOf(existing?.nombre ?: "") }
    var prioridad by rememberSaveable { mutableStateOf(existing?.prioridad ?: "Baja") }
    var urgencia by rememberSaveable { mutableStateOf(existing?.urgencia?.toString() ?: "1") }
    var activo by rememberSaveable { mutableStateOf(existing?.activo ?: "PC1") }
    var estado by rememberSaveable { mutableStateOf(existing?.estado ?: "Abierta") }
    var responsable by rememberSaveable { mutableStateOf(existing?.responsable ?: "") }
    var descripcion by rememberSaveable { mutableStateOf(existing?.descripcion ?: "") }
    var photoUrl by rememberSaveable { mutableStateOf(existing?.photoUrl ?: "") }

    LaunchedEffect(incidenciaId, ui.incidencias.size) {
        val e = ui.incidencias.firstOrNull { it.id == incidenciaId } ?: return@LaunchedEffect
        nombre = e.nombre
        prioridad = e.prioridad
        urgencia = e.urgencia.toString()
        activo = e.activo
        estado = e.estado
        responsable = e.responsable
        descripcion = e.descripcion
        photoUrl = e.photoUrl
    }

    Scaffold(topBar = { Text(if (isEdit) "Editar incidencia" else "Nueva incidencia") }) { inner ->
        Column(
            Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(prioridad, { prioridad = it }, label = { Text("Prioridad (Baja/Media/Alta)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(urgencia, { urgencia = it }, label = { Text("Urgencia (1/2/3)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(activo, { activo = it }, label = { Text("Activo (PC1-PC4)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(estado, { estado = it }, label = { Text("Estado (Abierta/Resuelta/Rechazada)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(responsable, { responsable = it }, label = { Text("Responsable") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(descripcion, { descripcion = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(photoUrl, { photoUrl = it }, label = { Text("URL Foto") }, modifier = Modifier.fillMaxWidth())

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = {
                    val urgenciaInt = urgencia.toIntOrNull() ?: 1
                    val entity = IncidenciaEntity(
                        id = existing?.id ?: 0L,
                        nombre = nombre.trim(),
                        prioridad = prioridad.trim(),
                        urgencia = urgenciaInt,
                        activo = activo.trim(),
                        estado = estado.trim(),
                        responsable = responsable.trim(),
                        descripcion = descripcion.trim(),
                        photoUrl = photoUrl.trim()
                    )
                    if (isEdit) vm.update(entity) else vm.add(entity)
                    onDone()
                }) { Text(if (isEdit) "Guardar cambios" else "Registrar") }

                OutlinedButton(onClick = onDone) { Text("Cancelar") }
            }
        }
    }
}
