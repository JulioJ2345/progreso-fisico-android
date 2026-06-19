package com.julioj.progresofisico.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.julioj.progresofisico.data.repository.ActividadRepository

class ActividadViewModelFactory(
    private val actividadRepository: ActividadRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(ActividadViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ActividadViewModel(
                actividadRepository = actividadRepository
            ) as T
        }

        throw IllegalArgumentException("ViewModel desconocido")
    }
}