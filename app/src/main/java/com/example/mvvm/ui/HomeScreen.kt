package com.example.mvvm.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onGoList: () -> Unit,
    onGoRegister: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Incidencias · MVVM") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Bienvenido",
                style = MaterialTheme.typography.headlineMedium
            )
            Text("Elige una opción para continuar")
            Button(onClick = onGoList, modifier = Modifier.fillMaxWidth()) {
                Text("Listado de incidencias")
            }
            Button(onClick = onGoRegister, modifier = Modifier.fillMaxWidth()) {
                Text("Registrar incidencia")
            }
        }
    }
}
