package com.horizon.service.serviceapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.horizon.service.serviceapplication.network.ApiService

class ServiceRequestViewModelFactory(
    private val apiService: ApiService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServiceRequestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ServiceRequestViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
