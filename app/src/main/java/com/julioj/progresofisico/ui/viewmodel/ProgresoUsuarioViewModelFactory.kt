package com.julioj.progresofisico.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.julioj.progresofisico.data.repository.ProgresoUsuarioRepository

class ProgresoUsuarioViewModelFactory(
    private val progresoUsuarioRepository: ProgresoUsuarioRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProgresoUsuarioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProgresoUsuarioViewModel(progresoUsuarioRepository) as T
        }

        throw IllegalArgumentException("ViewModel desconocido")
    }
}