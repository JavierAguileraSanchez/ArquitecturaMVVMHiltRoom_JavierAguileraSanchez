package com.example.mvvm.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mvvm.data.db.IncidenciaEntity
import com.example.mvvm.viewmodel.IncidenciasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListadoIncidenciasScreen(
    vm: IncidenciasViewModel = hiltViewModel(),
    onAdd: () -> Unit = {},
    onEdit: (Long) -> Unit,
    onBack: () -> Unit = {}
) {
    val ui by vm.ui.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Incidencias (${ui.incidencias.size})") },
                colors = centerAlignedTopAppBarColors()
            )
        },
        floatingActionButton = { FloatingActionButton(onClick = onAdd) { Text("+") } }
    ) { inner ->
        if (ui.incidencias.isEmpty()) {
            Box(
                Modifier.padding(inner).fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { Text("No hay ninguna incidencia") }
        } else {
            LazyColumn(
                modifier = Modifier.padding(inner).fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items = ui.incidencias, key = { it.id }) { i ->
                    IncidenciaCard(
                        i = i,
                        onClick = { onEdit(i.id) },
                        onDelete = { vm.deleteById(i.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun IncidenciaCard(
    i: IncidenciaEntity,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    var showConfirm by remember { mutableStateOf(false) }

    if (showConfirm) {
        AlertDialog(
            onDismissRequest = { showConfirm = false },
            title = { Text("Borrar incidencia") },
            text = { Text("¿Seguro que quieres borrar la incidencia '${i.nombre}'?") },
            confirmButton = {
                TextButton(onClick = { showConfirm = false; onDelete() }) {
                    Text("Borrar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirm = false }) { Text("Cancelar") }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text("${i.nombre}", style = MaterialTheme.typography.titleMedium)
            Text("Prioridad: ${i.prioridad} · Urgencia: ${i.urgencia}", style = MaterialTheme.typography.bodyMedium)
            Text("Activo: ${i.activo} · Estado: ${i.estado}", style = MaterialTheme.typography.bodyMedium)
            Text("Responsable: ${i.responsable}", style = MaterialTheme.typography.bodyMedium)
            Text("Descripción: ${i.descripcion}", maxLines = 2, overflow = TextOverflow.Ellipsis)
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(if (i.photoUrl.isNotBlank()) i.photoUrl else "https://picsum.photos/seed/${i.id}/200/200")
                    .crossfade(true)
                    .build(),
                contentDescription = "Foto incidencia",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Spacer(Modifier.height(4.dp))
            Text("Fecha: ${java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(i.fechaCreacion)}",
                style = MaterialTheme.typography.labelMedium
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(onClick = { showConfirm = true }) { Icon(Icons.Filled.Delete, contentDescription = "Borrar") }
            }
        }
    }
}
