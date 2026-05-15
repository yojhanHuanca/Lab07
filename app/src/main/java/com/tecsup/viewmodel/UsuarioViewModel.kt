package com.tecsup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.data.dao.UsuarioDao
import com.tecsup.data.entity.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel(private val dao: UsuarioDao): ViewModel() {
    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> = _usuario

    private val _loginError = MutableStateFlow(false)
    val loginError: StateFlow<Boolean> = _loginError

    private val _registroExitoso = MutableStateFlow(false)
    val registroExitoso: StateFlow<Boolean> = _registroExitoso

    private val _totalRutinas = MutableStateFlow(0)
    val totalRutinas: StateFlow<Int> = _totalRutinas

    private val _volumenTotal = MutableStateFlow(0.0)
    val volumenTotal: StateFlow<Double> = _volumenTotal

    fun login(nombreUsuario: String, password: String) {
        viewModelScope.launch {
            val result = dao.buscarPorCredenciales(nombreUsuario, password)
            if (result != null) {
                _usuario.value = result
                _loginError.value = false
            } else {
                _loginError.value = true
            }
        }
    }

    fun registrar(usuario: Usuario, onExiste: () -> Unit) {
        viewModelScope.launch {
            if (dao.buscarPorNombreUsuario(usuario.nombreUsuario) != null) {
                onExiste(); return@launch
            }
            dao.insertar(usuario)
            _registroExitoso.value = true
        }
    }

    fun cargarUsuario(id: Int) {
        viewModelScope.launch { _usuario.value = dao.buscarPorId(id) }
    }

    fun cargarEstadisticas(id: Int) {
        viewModelScope.launch {
            _totalRutinas.value = dao.contarRutinas(id)
            _volumenTotal.value = dao.sumarVolumenTotal(id) ?: 0.0
        }
    }

    fun limpiarLoginError() { _loginError.value = false }
    fun resetRegistro() { _registroExitoso.value = false }

}