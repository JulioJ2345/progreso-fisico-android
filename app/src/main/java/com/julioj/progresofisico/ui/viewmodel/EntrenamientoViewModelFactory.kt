package com.julioj.progresofisico.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.julioj.progresofisico.data.repository.EjercicioRepository
import com.julioj.progresofisico.data.repository.RegistroEntrenamientoRepository

class EntrenamientoViewModelFactory(
    private val ejercicioRepository: EjercicioRepository,
    private val registroRepository: RegistroEntrenamientoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(EntrenamientoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EntrenamientoViewModel(
                ejercicioRepository = ejercicioRepository,
                registroRepository = registroRepository
            ) as T
        }

        throw IllegalArgumentException("ViewModel desconocido")
    }
}