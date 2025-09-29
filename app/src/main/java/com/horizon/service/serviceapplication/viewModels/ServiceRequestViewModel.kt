package com.horizon.service.serviceapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.horizon.service.serviceapplication.model.ServiceRequest
import com.horizon.service.serviceapplication.model.ServiceResponse
import com.horizon.service.serviceapplication.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ServiceRequestUiState {
    object Idle : ServiceRequestUiState()
    object Loading : ServiceRequestUiState()
    data class Success(val data: ServiceResponse) : ServiceRequestUiState()
    data class Error(val message: String) : ServiceRequestUiState()
}

class ServiceRequestViewModel(private val apiService: ApiService) : ViewModel() {

    private val _requestState = MutableStateFlow<ServiceRequestUiState>(ServiceRequestUiState.Idle)
    val requestState: StateFlow<ServiceRequestUiState> = _requestState

    fun submitRequest(request: ServiceRequest) {
        viewModelScope.launch {
            _requestState.value = ServiceRequestUiState.Loading
            try {
                val response = apiService.makeRequest(request)
                _requestState.value = ServiceRequestUiState.Success(response)
            } catch (e: Exception) {
                _requestState.value = ServiceRequestUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun resetState() {
        _requestState.value = ServiceRequestUiState.Idle
    }
}
