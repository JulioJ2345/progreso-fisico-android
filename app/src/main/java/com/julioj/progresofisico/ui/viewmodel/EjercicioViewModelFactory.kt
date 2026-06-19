package com.julioj.progresofisico.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.julioj.progresofisico.data.repository.EjercicioRepository

class EjercicioViewModelFactory(
    private val ejercicioRepository: EjercicioRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(EjercicioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EjercicioViewModel(ejercicioRepository) as T
        }

        throw IllegalArgumentException("ViewModel desconocido")
    }
}