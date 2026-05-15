package com.tecsup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.data.dao.RutinaDao
import com.tecsup.data.entity.Rutina
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class RoutineViewModel(private val dao: RutinaDao) : ViewModel() {

    private val _rutinas = MutableStateFlow<List<Rutina>>(emptyList())
    val rutinas: StateFlow<List<Rutina>> = _rutinas

    private val _rutinaActual = MutableStateFlow<Rutina?>(null)
    val rutinaActual: StateFlow<Rutina?> = _rutinaActual

    fun cargarRutinas(usuarioId: Int) {
        viewModelScope.launch { _rutinas.value = dao.listarPorUsuario(usuarioId) }
    }

    fun cargarRutinaPorId(id: Int) {
        viewModelScope.launch { _rutinaActual.value = dao.buscarPorId(id) }
    }

    fun insertar(rutina: Rutina, onSuccess: () -> Unit) {
        viewModelScope.launch { dao.insertar(rutina); onSuccess() }
    }

    fun actualizar(rutina: Rutina, onSuccess: () -> Unit) {
        viewModelScope.launch { dao.actualizar(rutina); onSuccess() }
    }

    fun eliminar(rutina: Rutina, usuarioId: Int) {
        viewModelScope.launch { dao.eliminar(rutina); cargarRutinas(usuarioId) }
    }
}