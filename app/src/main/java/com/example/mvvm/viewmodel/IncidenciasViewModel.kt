package com.example.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.data.IncidenciasRepository
import com.example.mvvm.data.db.IncidenciaEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class IncidenciasUiState(
    val loading: Boolean = true,
    val incidencias: List<IncidenciaEntity> = emptyList(),
    val error: String? = null
)

sealed interface UiEvent {
    data class Message(val text: String) : UiEvent
}

@HiltViewModel
class IncidenciasViewModel @Inject constructor(
    private val repo: IncidenciasRepository
) : ViewModel() {

    private val _ui = MutableStateFlow(IncidenciasUiState())
    val ui: StateFlow<IncidenciasUiState> = _ui.asStateFlow()

    private val _events = MutableSharedFlow<UiEvent>()
    val events: SharedFlow<UiEvent> = _events.asSharedFlow()

    init {
        viewModelScope.launch {
            repo.observeAll()
                .onStart { _ui.value = _ui.value.copy(loading = true, error = null) }
                .catch { _ui.value = _ui.value.copy(loading = false, error = it.message) }
                .collect { list -> _ui.value = IncidenciasUiState(loading = false, incidencias = list) }
        }
    }

    fun add(incidencia: IncidenciaEntity) = viewModelScope.launch {
        repo.add(incidencia)
        _events.emit(UiEvent.Message("Incidencia registrada"))
    }

    fun update(incidencia: IncidenciaEntity) = viewModelScope.launch {
        repo.update(incidencia)
        _events.emit(UiEvent.Message("Incidencia actualizada"))
    }

    fun deleteById(id: Long) = viewModelScope.launch {
        repo.deleteById(id)
        _events.emit(UiEvent.Message("Incidencia borrada"))
    }

    fun incidenciaFlow(id: Long): Flow<IncidenciaEntity?> = repo.observeById(id)
}
