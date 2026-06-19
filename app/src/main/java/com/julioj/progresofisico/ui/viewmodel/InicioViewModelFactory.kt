package com.julioj.progresofisico.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.julioj.progresofisico.data.repository.EjercicioRepository

class InicioViewModelFactory(
    private val ejercicioRepository: EjercicioRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(InicioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InicioViewModel(ejercicioRepository) as T
        }

        throw IllegalArgumentException("ViewModel desconocido")
    }
}